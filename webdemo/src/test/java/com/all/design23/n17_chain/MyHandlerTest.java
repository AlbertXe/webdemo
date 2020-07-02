package com.all.design23.n17_chain;

import org.junit.Test;


public class MyHandlerTest {
    @Test
    public void test() {
        MyHandler h1 = new MyHandler("h1");
        MyHandler h2 = new MyHandler("h2");
        MyHandler h3 = new MyHandler("h3");
        h3.setHandler(h2);
        h2.setHandler(h1);

        h3.operate();
    }

}