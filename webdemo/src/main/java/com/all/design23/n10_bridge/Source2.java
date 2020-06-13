package com.all.design23.n10_bridge;

import com.all.design23.n06_adapter.inter.Sourceable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Source2 implements Sourceable {
    @Override
    public void method1() {
        log.info("source2");
    }

    @Override
    public void method2() {

    }
}
