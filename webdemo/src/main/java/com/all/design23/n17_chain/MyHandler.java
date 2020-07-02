package com.all.design23.n17_chain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyHandler extends AbstractHandler implements Handler {

    private String name;

    public MyHandler(String name) {
        this.name = name;
    }


    @Override
    public void operate() {
        log.info("handler " + name);
        if (getHandler() != null) {
            getHandler().operate();
        }
    }

}
