package com.all.design23.n02_factorymethod;

import com.all.design23.n01_factory.Sender;

/**
 * 工厂方法模式 创建一个工厂接口和创建多个工厂实现类，这样一旦需要增加新的功能，直接增加新的工厂类就可以了，不需要修改之前的代码。
 */
public interface Provider {
    Sender produce();
}
