package com.all.design23.n15_observer;

/**
 * 观察者模式（observer）   类与类关系   关系模式（11-3）
 * 当一个对象变化时，其它依赖该对象的对象都会收到通知，并且随着变化！对象之间是一种一对多的关系;类似订阅
 */
public interface ObServer {
    void update();
}
