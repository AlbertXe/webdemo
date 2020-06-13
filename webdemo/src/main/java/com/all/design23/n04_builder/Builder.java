package com.all.design23.n04_builder;

import com.all.design23.n01_factory.MailSender;
import com.all.design23.n01_factory.Sender;
import com.all.design23.n01_factory.SmsSender;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 工厂类模式提供的是创建单个类的模式，而建造者模式则是将各种产品集中起来进行管理，用来创建复合对象
 */
public class Builder {
    private List<Sender> senders = new ArrayList<>();

    public void productMailSender(int c) {
        for (int i = 0; i < c; i++) {
            senders.add(new MailSender());
        }
    }

    public void productSmsSender(int c) {
        for (int i = 0; i < c; i++) {
            senders.add(new SmsSender());
        }
    }


    @Test
    public void test() {
        Builder builder = new Builder();
        builder.productMailSender(10);
    }
}
