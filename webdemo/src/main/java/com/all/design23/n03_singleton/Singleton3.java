package com.all.design23.n03_singleton;

/**
 * 恶汉模式
 */
public class Singleton3 {
    private static Singleton3 instance = new Singleton3();

    private Singleton3() {
    }

    public static Singleton3 getInstance() {
        return instance;
    }


}
