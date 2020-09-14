package com.config.transaction;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-09-12 12:49
 */
public class DataSourceHolder {
    private static ThreadLocal<Object> threadLocal = new InheritableThreadLocal<>();

    public static Object get() {
        return threadLocal.get();
    }

    public static void set(Object object) {
        threadLocal.set(object);

    }

    public static void remove() {
        threadLocal.remove();
    }
}
