package com.all.design23.n09_facade;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Disk {
    public void start() {
        log.info("disk started");
    }

    public void shutdown() {
        log.info("disk shutdown");
    }
}
