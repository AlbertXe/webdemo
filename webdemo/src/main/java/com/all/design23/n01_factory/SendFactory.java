package com.all.design23.n01_factory;

/**
 * 普通工厂模式
 */
public class SendFactory {
    public Sender product(String type) {
        if (type.equals("mail")) {
            return new MailSender();
        } else if (type.equals("sms")) {
            return new SmsSender();
        }
        return null;
    }
}
