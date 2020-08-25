package com.util.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Java使用ProcessBuilder类调用外部程序
 */
public class ProcessUtil {

    public static void main(String[] args) throws IOException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("ipconfig");

        //将标准输入流和错误输入流合并，通过标准输入流读取信息
        builder.redirectErrorStream(true);
        Process process = builder.start();
        InputStream inputStream = process.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            System.out.println(line);
        }
        inputStream.close();
    }

}
