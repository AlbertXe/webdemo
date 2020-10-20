package com.util.file;

public class Main {

    public static void main(String[] args) {
        BigFileReader.Builder builder = new BigFileReader.Builder("d:/a.txt");

        builder.withThreadSize(10)
                .withCharset("gbk")
                .withBuffSize(1024)
                .withConsumer(line -> {
                    System.out.println(line);
                });
        BigFileReader bigFileReader = builder.build();
        bigFileReader.start();
    }

}
