package com.all.design23.n01_factory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MailSender implements Sender {
    @Override
    public void send() {
        log.info("mail sender");
    }
}
