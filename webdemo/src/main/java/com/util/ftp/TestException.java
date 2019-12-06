package com.util.ftp;

import java.io.FileNotFoundException;

/**
 * @author AlbertXe
 * @date 2019-12-05 15:47
 */
public class TestException {
    public volatile static boolean flag = false;

    public static void main(String[] args) {

        Thread my = new MyThread();
        my.start();
        while (true) {
            if (flag) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("开始打断线程");
                my.stop();
                System.out.println("打断线程结束");
                break;
            }
        }

    }


}

class MyThread extends Thread {

    @Override
    public void run() {
        try {
            long s = System.currentTimeMillis();
            SftpUtils.upload("D:\\App_linux\\apache-maven-3.6.3-bin.tar.gz", "/upload");
            long e = System.currentTimeMillis();
            System.out.println((e-s)+"ms");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
