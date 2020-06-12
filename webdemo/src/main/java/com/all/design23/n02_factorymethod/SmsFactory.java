package com.all.design23.n02_factorymethod;

import com.all.design23.n01_factory.Sender;
import com.all.design23.n01_factory.SmsSender;

public class SmsFactory implements Provider {
    @Override
    public Sender produce() {
        return new SmsSender();
    }
}
