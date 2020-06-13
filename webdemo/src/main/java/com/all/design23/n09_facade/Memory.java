package com.all.design23.n09_facade;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Memory {
    public void start() {
        log.info("memory started");
    }

    public void shutdown() {
        log.info("memory shutdown");
    }
}
