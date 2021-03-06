package com.all.design23.n06_adapter.inter;

/**
 * 接口适配器
 * <p>
 * 我们引入了接口的适配器模式，借助于一个抽象类，该抽象类实现了该接口，实现了所有的方法，
 * 而我们不和原始的接口打交道，只和该抽象类取得联系，所以我们写一个类，继承该抽象类，重写我们需要的方法就行
 */
public class Adapter3 implements Sourceable {

    @Override
    public void method1() {

    }

    @Override
    public void method2() {

    }
}
