package com.util.jvm.ch01;

import java.util.LinkedList;
import java.util.List;

/**
 * -Xms5m -Xmx5m -XX:+PrintGC -XX:+HeapDumpOnOutOfMemoryError
 */
public class Oom {
    public static void main(String[] args) {
        List list = new LinkedList<>();

        int i = 0;
        while (true) {
            i++;
            if (i % 10000 == 0) {
                System.out.println(i);
            }
            list.add(new Object());

        }
//        String[] s = new String[999999999];
    }
}
