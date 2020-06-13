package com.all.design23.n07_decorator;

import lombok.extern.slf4j.Slf4j;

/**
 * 装饰器模式
 * 装饰模式就是给一个对象增加一些新的功能，而且是动态的，要求装饰对象和被装饰对象实现同一个接口，装饰对象持有被装饰对象的实例
 * <p>
 * 缺点：产生过多相似的对象，不易排错！
 */
@Slf4j
public class Decorator implements Sourceable {
    private Source source;

    public Decorator(Source source) {
        this.source = source;
    }

    @Override
    public void method() {
        log.info("before method");
        source.method();
        log.info("after method");
    }
}
