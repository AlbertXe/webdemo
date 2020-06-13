package com.all.design23.n10_bridge;

/**
 * 桥接模式
 * 将抽象化与实现化解耦，使得二者可以独立变化，像我们常用的JDBC桥DriverManager一样，
 * JDBC进行连接数据库的时候，在各个数据库之间进行切换
 */
public class MyBridge extends Bridge {

    public void method() {
        getSourceable().method1();
    }
}
