package com.all.design23.n03_singleton;

public class Singleton2 {
    /**
     * 双段锁  static volatile
     */
    private static volatile Singleton2 instance;

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        if (instance == null) {
            synchronized (instance) {
                instance = new Singleton2();
            }
        }
        return instance;
    }
}
