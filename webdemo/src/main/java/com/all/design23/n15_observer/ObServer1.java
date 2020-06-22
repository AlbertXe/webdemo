package com.all.design23.n15_observer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObServer1 implements ObServer {

    @Override
    public void update() {
        log.info("ObServer1......");
    }
}
