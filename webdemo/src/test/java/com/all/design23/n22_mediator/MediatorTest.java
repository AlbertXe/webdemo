package com.all.design23.n22_mediator;


import org.junit.Test;

public class MediatorTest {

    @Test
    public void test() {
        MyMediator mediator = new MyMediator();
        mediator.createMediator();
        mediator.workAll();
    }

}