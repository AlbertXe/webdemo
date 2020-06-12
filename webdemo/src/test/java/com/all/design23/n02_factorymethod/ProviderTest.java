package com.all.design23.n02_factorymethod;

import org.junit.Test;

public class ProviderTest {
    @Test
    public void test() {
        Provider provider = new SmsFactory();
        provider.produce().send();

        Provider mail = new MailFactory();
        mail.produce().send();
    }

}