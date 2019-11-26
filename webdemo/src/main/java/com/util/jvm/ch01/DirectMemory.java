package com.util.jvm.ch01;

import java.nio.ByteBuffer;

/**
 * -Xmx10m -XX:MaxDirectMemorySize=10m
 * 直接内存
 * AlbertXe
 * 2019/11/26 21:29
 */
public class DirectMemory {

    public static void main(String[] args) {
        //14M 直接内存
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 1024 * 14);

    }
}
