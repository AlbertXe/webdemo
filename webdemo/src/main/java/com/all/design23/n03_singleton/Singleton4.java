package com.all.design23.n03_singleton;

/**
 * 内部类
 */
public class Singleton4 {
    private Singleton4() {

    }

    private static class SingletonFactory {
        private static Singleton4 instance = new Singleton4();
    }

    public Singleton4 getInstance() {
        return SingletonFactory.instance;
    }

}
