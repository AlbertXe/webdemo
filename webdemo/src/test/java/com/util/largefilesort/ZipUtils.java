package com.util.largefilesort;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 压缩 解压
 */
@Slf4j
public class ZipUtils {
    private static String dest;

    /**
     * 压缩文件
     *
     * @param dir 目录
     * @param os  输出流
     */
    public static void toZip(String dir, OutputStream os) {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        zos = new ZipOutputStream(os);

        File f = new File(dir);
        compress(f, zos, f.getName());
        long end = System.currentTimeMillis();
    }

    public static void unzip(ZipInputStream zis) {
        try {
            ZipEntry zipEntry = zis.getNextEntry();
            if (zipEntry != null) {
                File file = new File(dest + "/" + zipEntry.getName());
                if (file.isDirectory()) {
                    file.mkdirs();
                    unzip(zis);
                } else {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    FileOutputStream os = new FileOutputStream(file);
                    int len = -1;
                    byte[] bs = new byte[1024];
                    while ((len = zis.read(bs)) != -1) {
                        os.write(bs, 0, len);
                    }
                    os.close();
                    zis.closeEntry();
                }

            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private static void compress(File f, ZipOutputStream zos, String name) {
        byte[] b = new byte[2 * 1024];
        if (f.isFile()) {
            try {
                // zos输出流中添加zip实体
                zos.putNextEntry(new ZipEntry(name));
                FileInputStream is = new FileInputStream(f);
                int len = -1;
                while ((len = is.read(b)) != -1) {
                    zos.write(b, 0, len);
                }
                //结束
                zos.flush();
                is.close();
                zos.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            File[] files = f.listFiles();
            if (files == null || files.length == 0) {
                //保留原来的目录结构
                try {
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    zos.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    zos.putNextEntry(new ZipEntry(name + "/"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (File file : files) {
                    compress(file, zos, name + "/" + file.getName());

                }
            }
        }
    }

    @Test
    public void testToZip() throws FileNotFoundException {
        FileOutputStream os = new FileOutputStream("D:\\mytestZip.zip");
        toZip("D:\\test", os);
    }

    @Test
    public void testUnzip() throws FileNotFoundException {
        String src = "D:\\mytestZip.zip";
        dest = "D:\\test1";
        ZipInputStream zis = new ZipInputStream(new FileInputStream(src));
        unzip(zis);
    }
}
