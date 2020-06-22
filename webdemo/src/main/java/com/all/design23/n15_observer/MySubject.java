package com.all.design23.n15_observer;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

@Slf4j
public class MySubject extends AbstractSubject {

    @Override
    public void operation() {
        log.info("update self");
        notifyObservers();
    }

    @Test
    public void test1() {
        Subject subject = new MySubject();
        subject.add(new ObServer1());
        subject.add(new ObServer1());

        subject.operation();
    }


    @Test
    public void test() throws UnsupportedEncodingException {
        String s = "中文123";
        System.out.println(s.getBytes("gbk").length);
        System.out.println(s.getBytes("utf-8").length);
    }
}
