package com.util.largefilesort;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 超大文件排序
 */
@Slf4j
public class LargeFileSort {
    static CountDownLatch doneSignal;
    static String[] genFiles = new String[10];
    static String ROOT_PATH = "D:\\20200411\\";
    static int SIZE = 1024 * 1; // 1M
    static List<File> tempFiles = new CopyOnWriteArrayList<>();


    public static void main(String[] args) {
        long s = System.currentTimeMillis();
        generateFiles();
        log.info("创建耗时{}", System.currentTimeMillis() - s);

        //操作的文件时准备的10个大文件
        long l = System.currentTimeMillis();
        divisionAndSortFile(2);
        log.info("切分数据耗时：{}", System.currentTimeMillis() - l);

        long m = System.currentTimeMillis();
        merge();
        log.info("合并耗时{}", System.currentTimeMillis() - m);


    }

    private static void merge() {
        List<File> files = new ArrayList<>(tempFiles);
        tempFiles.clear();
        if (files.size() >= 20) {
            File f = new File(ROOT_PATH + "result");
            merge(files, f);
        } else {
            BlockThreadPool pool = new BlockThreadPool(3);
            doneSignal = new CountDownLatch(20);
            List<List<File>> fileList = new ArrayList<>();
            for (int i = 0; i < files.size(); i++) {

            }
            pool.execute(() -> {

            });
            try {
                doneSignal.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private static void mergeFile(List<File> files, File f) {

    }

    private static void merge(List<File> files, File f) {
        List<FileEntity> fileEntities = new ArrayList<>();
        try {
            for (File file : files) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                FileEntity fileEntity = new FileEntity(reader);
                fileEntities.add(fileEntity);
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            String line = null;
            FileEntity fileEntity = null;
            StringBuilder sb = new StringBuilder();
            long i = 0;
            while ((fileEntity = getFirstLine(fileEntities)) != null) {
                i++;
                sb.append(fileEntity.getLine()).append("\n");
                if ((i + 1) % 10000 == 0) {
                    writer.write(sb.toString());
                    sb.setLength(0);
                    writer.flush();
                }
                fileEntity.nextLine();
            }
            if (sb.length() > 0) {
                writer.write(sb.toString());
                sb = null;
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得第一行数据
     *
     * @param fileEntities
     * @return
     */
    private static FileEntity getFirstLine(List<FileEntity> fileEntities) {
        Iterator<FileEntity> iterator = fileEntities.iterator();
        while (iterator.hasNext()) {
            FileEntity fileEntity = iterator.next();
            if (fileEntity.getLine() == null) {
                fileEntity.close();
                iterator.remove();
            }
        }

        if (fileEntities.size() == 0) {
            return null;
        }
        fileEntities.sort(Comparator.comparingInt(FileEntity::getId));
        return fileEntities.get(0);
    }

    private static void divisionAndSortFile(int i) {
        BlockThreadPool pool = new BlockThreadPool(i);
        doneSignal = new CountDownLatch(10);
        for (int i1 = 0; i1 < genFiles.length; i1++) {
            String file = genFiles[i1];
            pool.execute(() -> {
                divWork(file, SIZE);
            });
        }
        try {
            doneSignal.await();
            log.info("等待拆分结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }

    private static void divWork(String file, int size) {
        File f = new File(file);
        if (!f.exists()) throw new RuntimeException("文件不存在" + file);

        long length = f.length();
        int sum = (int) (Math.ceil(length / size) + 1);//临时文件总数
        log.info("sum={}", sum);
        List<File> temps = createTempFiles(f, sum);//创建临时文件
        tempFiles.addAll(temps);//用arrayList存在线程安全问题
        divSort(f, temps);
        doneSignal.countDown();
    }

    private static void divSort(File f, List<File> temps) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(f));
            //确定每个文件内容
            int fileNo = temps.size() - 1;
            List<String> lines = new ArrayList<>();
            String line = null;
            int sum = 0;
            while ((line = reader.readLine()) != null) {
                line += "\n";
                sum += line.getBytes().length;
                if (sum >= SIZE) {//未将改行写入
                    pushLinesToFile(temps.get(fileNo), lines);
                    fileNo--;
                    sum = line.getBytes().length;
                    lines.clear();
                }

                lines.add(line);
            }
            if (lines.size() > 0) {
                pushLinesToFile(temps.get(0), lines);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将数据写入文件
     *
     * @param file
     * @param lines
     */
    private static void pushLinesToFile(File file, List<String> lines) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            lines.sort(String::compareTo);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < lines.size(); i++) {
                sb.append(lines.get(i));
                if ((i + 1) % 1000 == 0) {
                    fos.write(sb.toString().getBytes());
                    sb.setLength(0);
                }
            }
            if (sb.length() > 0) {
                fos.write(sb.toString().getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static List<File> createTempFiles(File f, int sum) {
        List<File> temps = new ArrayList<>();
        String fol = f.getParent();
        for (int i = 0; i < sum; i++) {
            String fileName = f.getName() + ".tmp" + i;
            File file = new File(fol + "/" + fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            temps.add(file);
        }
        return temps;
    }

    /**
     * 10个线程生成10个文件
     */
    private static void generateFiles() {
        ExecutorService service = Executors.newFixedThreadPool(10);
        doneSignal = new CountDownLatch(10);
        for (int i = 0; i < genFiles.length; i++) {
            String file = ROOT_PATH + "test" + i;
            genFiles[i] = file;
            service.submit(() -> {
                generateFile(file);
                log.info("生产文件成功{}", file);
                doneSignal.countDown();
            });
        }
        try {
            //等待文件生成
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdown();

    }


    /**
     * 生产文件 千万行数据
     *
     * @param filePath
     */
    public static void generateFile(String filePath) {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        RandomAccessFile raf = null;
        FileChannel channel = null;
        File file = new File(filePath);
        if (file.exists()) file.delete();
        try {
            file.createNewFile();
            raf = new RandomAccessFile(file, "rw");
            channel = raf.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024 << 10);//1M
            for (int i = 0; i < 10000 * 1; i++) {
                sb.append(r.nextInt(10000)).append("\n");
                //分批写入数据
                if ((i + 1) % 10000 == 0) {
                    byteBuffer.put(sb.toString().getBytes());
                    sb.setLength(0);
                    byteBuffer.flip();
                    channel.write(byteBuffer);
                    byteBuffer.clear();
                }
            }
            //最后一页
            if (sb.length() > 0) {
                byteBuffer.put(sb.toString().getBytes());
                byteBuffer.flip();
                channel.write(byteBuffer);
                byteBuffer.clear();
            }

        } catch (IOException e) {
            log.error("生产测试文件失败：{}", e);
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                }
            }
            if (channel.isOpen()) {
                try {
                    channel.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
