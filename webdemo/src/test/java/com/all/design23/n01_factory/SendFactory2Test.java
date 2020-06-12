package com.all.design23.n01_factory;

import org.junit.Test;

public class SendFactory2Test {
    @Test
    public void test() {
        SendFactory2.productMail().send();
        SendFactory2.productSms().send();
    }

}