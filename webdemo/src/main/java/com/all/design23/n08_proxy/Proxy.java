package com.all.design23.n08_proxy;

import com.all.design23.n07_decorator.Source;
import com.all.design23.n07_decorator.Sourceable;
import lombok.extern.slf4j.Slf4j;

/**
 * 代理模式
 * 和外观模式相似   动态代理
 */
@Slf4j
public class Proxy implements Sourceable {
    private Source source;

    public Proxy(Source source) {
        this.source = source;
    }

    @Override
    public void method() {
        log.info("before");
        source.method();
        log.info("after");
    }
}
