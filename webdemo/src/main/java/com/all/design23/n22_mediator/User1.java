package com.all.design23.n22_mediator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class User1 extends User {
    public User1(Mediator mediator) {
        super(mediator);
    }

    @Override
    public void work() {
        log.info("user1  work");
    }
}
