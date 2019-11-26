package com.util.jvm.ch02;

import com.pojo.User;

import java.lang.ref.WeakReference;

/**
 * 弱引用
 * AlbertXe
 * 2019/11/26 22:14
 */
public class WeakRef {
    public static void main(String[] args) {
        User user = new User("1", "albertXe");
        WeakReference<User> softReference = new WeakReference<>(user);
        //强引用断开， 还有弱引用，会在oom之前被回收
        user = null;
        System.out.println(softReference.get());
        System.gc();// 所有与GC有关  GC之前就会回收
        System.out.println("afrerGC"+softReference.get());

    }
}
