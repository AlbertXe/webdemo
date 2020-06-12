package com.all.design23.n22_mediator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class User2 extends User {
    public User2(Mediator mediator) {
        super(mediator);
    }

    @Override
    public void work() {
        log.info("user2 work");
    }
}
