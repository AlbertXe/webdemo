package com.all.design23.n02_factorymethod;

import com.all.design23.n01_factory.MailSender;
import com.all.design23.n01_factory.Sender;

public class MailFactory implements Provider {
    @Override
    public Sender produce() {
        return new MailSender();
    }
}
