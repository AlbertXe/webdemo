package com.all.design23.n07_decorator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Source implements Sourceable {
    @Override
    public void method() {
        log.info("this is method");
    }
}
