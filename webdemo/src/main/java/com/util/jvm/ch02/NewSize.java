package com.util.jvm.ch02;

import java.util.ArrayList;
import java.util.List;

/**
 * -Xms20m -XX:+PrintGCDetails -Xmn7m -XX:SurvivorRatio=2
 * 新生代大小
 * AlbertXe
 * 2019/11/27 21:38
 */
public class NewSize {

    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new byte[1024 * 1024]);
        }
        
    }
}
