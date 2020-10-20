package com.util.file;

import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * @description: 单文件读取
 * @author: AlbertXe
 * @create: 2020-10-20 12:50
 */
public class BigFileReader {
    private int threadSize;
    private int buffSize;
    private String charset;
    private Consumer consumer;

    private long fileLength;
    private ExecutorService executorService;
    private RandomAccessFile rAccessFile;
    private CyclicBarrier cyclicBarrier;
    private AtomicLong count;
    private Set<StartEndPair> pairs = new HashSet<>();


    public BigFileReader(File file, int threadSize, int buffSize, String charset, Consumer consumer) {
        this.threadSize = threadSize;
        this.buffSize = buffSize;
        this.charset = charset;
        this.consumer = consumer;
        fileLength = file.length();
        executorService = Executors.newFixedThreadPool(threadSize);
        try {
            rAccessFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        count = new AtomicLong(0L);
    }

    public void start() {
        long eachSize = fileLength / threadSize;
        try {
            calStartEnd(0, eachSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long s = System.currentTimeMillis();
        cyclicBarrier = new CyclicBarrier(pairs.size(), () -> {
            System.out.println("all time :" + (System.currentTimeMillis() - s));
            System.out.println("sum: " + count.get());
        });

        for (StartEndPair pair : pairs) {
            System.out.println("分片:" + pair);
            executorService.submit(new ReadTask(pair));
        }
        executorService.shutdown();
    }

    private class ReadTask implements Runnable {
        private long start;
        private long size;
        private byte[] readBuff;

        public ReadTask(StartEndPair pair) {
            start = pair.start;
            size = pair.end - 1 - start;
            readBuff = new byte[buffSize];
        }

        @Override
        public void run() {
            try {
                // 快速读写技术 内存镜像 文件不能超过1.5G
                MappedByteBuffer map = rAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, start, size);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                for (int i = 0; i < size; i += buffSize) {
                    int readLength;
                    if (i + buffSize <= size) {
                        readLength = buffSize;
                    } else {
                        readLength = (int) (size - i);
                    }
                    map.get(readBuff, 0, readLength);
                    for (int j = 0; j < readLength; j++) {
                        byte b = readBuff[j];
                        if (b == '\n' || b == '\r') {
                            handler(os.toByteArray());
                            os.reset();
                        } else {
                            os.write(b);
                        }
                    }
                }
                if (os.size() > 0) {
                    handler(os.toByteArray());
                }

                cyclicBarrier.await(); // 性能测试


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void handler(byte[] bs) {
            String line;
            if (charset == null) {
                line = new String(bs);
            } else {
                line = new String(bs, Charset.forName(charset));
            }
            if (StringUtils.isNotBlank(line)) {
                consumer.accept(line);
                count.incrementAndGet();
            }
        }
    }

    /**
     * 计算起始位置
     *
     * @param start
     * @param size
     */
    private void calStartEnd(long start, long size) throws IOException {
        if (start > fileLength - 1) {
            return;
        }
        StartEndPair pair = new StartEndPair();
        pair.start = start;
        long endPosition = start + size - 1;
        if (endPosition > fileLength - 1) {
            endPosition = fileLength - 1;
            pair.end = endPosition;
            pairs.add(pair);
            return;
        }
        rAccessFile.seek(endPosition);
        byte b = (byte) rAccessFile.read();
        while (b != '\n' && b != '\r') {
            endPosition++;
            if (endPosition > fileLength - 1) {
                endPosition = fileLength - 1;
                break;
            }
            rAccessFile.seek(endPosition);
            b = (byte) rAccessFile.read();
        }
        pair.end = endPosition;
        pairs.add(pair);
        calStartEnd(endPosition + 1, size);
    }

    @EqualsAndHashCode
    private class StartEndPair {
        public long start;
        public long end;
    }

    public static class Builder {
        private File file;
        private int threadSize = 10;
        private int buffSize = 1 << 20;
        private String charset = "gbk";
        private Consumer consumer;

        public Builder(String file) {
            this.file = new File(file);
        }

        public Builder withThreadSize(int threadSize) {
            this.threadSize = threadSize;
            return this;
        }

        public Builder withBuffSize(int buffSize) {
            this.buffSize = buffSize;
            return this;
        }

        public Builder withCharset(String charset) {
            this.charset = charset;
            return this;
        }

        public Builder withConsumer(Consumer consumer) {
            this.consumer = consumer;
            return this;
        }


        public BigFileReader build() {
            BigFileReader bigFileReader = new BigFileReader(file, threadSize, buffSize, charset, consumer);
            return bigFileReader;
        }
    }


}
