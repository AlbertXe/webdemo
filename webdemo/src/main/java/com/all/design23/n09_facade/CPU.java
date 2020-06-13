package com.all.design23.n09_facade;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CPU {
    public void start() {
        log.info("cpu started");
    }

    public void shutdown() {
        log.info("cpu shutdown");
    }
}
