package com.util.file;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 支持多线程对文件操作  未真正完成
 */
@Slf4j
public class FileShare {

    public static void write(String path, String content) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.info("create new file error,{}", e);
            }
        }

        try {
            RandomAccessFile out = new RandomAccessFile(file, "rw");
            out.seek(out.length()); // 表示文件末尾追加

            FileChannel channel = out.getChannel();
            FileLock lock = null;
            while (lock == null) {
                try {
                    log.info("开始获得锁");
                    lock = channel.tryLock();
                } catch (Exception e) {
                    log.info("有其他线程在操作文件，当前线程休眠等待1秒");
                    Thread.sleep(1000);
                }
            }

            Thread.sleep(1000); // 增加写文件时间
            out.write(content.getBytes("utf-8"));

            lock.release();
            channel.close();
            out.close();
            out = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
//        for (int i = 0; i < 2; i++) {
//            Thread thread = new Thread(()->{
//                FileShare.write("D:\\a.txt","hello , china");
//            });
//            thread.start();
//        }
        FileShare.write("D:\\a.txt", "hello , china");
    }
}
