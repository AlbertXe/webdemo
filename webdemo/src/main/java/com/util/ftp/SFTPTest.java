package com.util.ftp;

import org.junit.Test;

import java.io.FileNotFoundException;

/**
 * @author AlbertXe
 * @date 2019-12-05 15:36
 */
public class SFTPTest {

    @Test
    public void test1(){
        try {
            SftpUtils. upload("D:\\idea_study\\webdemo\\src\\main\\java\\com\\util\\ftp\\test.txt", "/upload");
        } catch (FileNotFoundException e) {
            System.out.println("上传失败");
            e.printStackTrace();
        }

    }

    @Test
    public void test2() {
        SftpUtils.exec("ls -l");
    }

    /**
     * 多线程执行
     */
    @Test
    public void test3() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    SftpUtils.upload("D:\\idea_study\\webdemo\\src\\main\\java\\com\\util\\ftp\\test.txt", "/upload");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
