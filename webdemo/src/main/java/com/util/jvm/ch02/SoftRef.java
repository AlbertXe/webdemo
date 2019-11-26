package com.util.jvm.ch02;

import com.pojo.User;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * -Xmx10m -Xms10m -XX:+PrintGC
 * 软引用
 * AlbertXe
 * 2019/11/26 21:59
 */
public class SoftRef {
    public static void main(String[] args) {
        User user = new User("1", "albertXe");
        SoftReference<User> softReference = new SoftReference<>(user);
        //强引用断开， 还有软引用，会在oom之前被回收
        user = null;
        System.out.println(softReference.get());
        System.gc();// 所有与GC无关
        System.out.println("afrerGC"+softReference.get());
        List<byte[]> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            System.out.println(i+"========="+softReference.get());
            try {
                list.add(new byte[1024*1024]);
            } catch (Throwable e) {
                System.out.println("错误了");
                System.out.println(i+"========="+softReference.get());
                e.printStackTrace();
            }
        }

    }

}