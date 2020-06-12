package com.all.design23.n01_factory;

import org.junit.Test;

public class SendFactoryTest {
    @Test
    public void test() {
        SendFactory factory = new SendFactory();
        factory.product("sms").send();
        factory.product("mail").send();
    }

}