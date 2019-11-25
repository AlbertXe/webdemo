package com.util.jvm.ch01;

/**
 * 元空间
 * -XX:MaxMetaspaceSize=3m
 */
public class MetaSpace {
    public static void main(String[] args) {
//        List list = new LinkedList<>();
//
//        int i = 0;
//        while (true) {
//            i++;
//            if (i % 10000 == 0) {
//                System.out.println(i);
//            }
//            list.add(new Object());
//
//        }
        String[] s = new String[999999999];
    }
}
