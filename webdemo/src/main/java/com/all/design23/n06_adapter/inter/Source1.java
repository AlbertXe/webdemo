package com.all.design23.n06_adapter.inter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Source1 extends Adapter3 {
    @Override
    public void method1() {
        log.info("source1");
    }
}
