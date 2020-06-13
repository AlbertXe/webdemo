package com.all.design23.n06_adapter.cls;

import lombok.extern.slf4j.Slf4j;

/**
 * 对象适配器
 */
@Slf4j
public class Adapter2 implements Targetable {
    private Source source;

    public Adapter2(Source source) {
        this.source = source;
    }

    @Override
    public void method1() {
        source.method1();
    }

    @Override
    public void method2() {
        log.info("this is method2");
    }
}
