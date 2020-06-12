package com.all.design23.n01_factory;

public class SendFactory2 {

    public static Sender productMail() {
        return new MailSender();
    }

    public static Sender productSms() {
        return new SmsSender();
    }

}
