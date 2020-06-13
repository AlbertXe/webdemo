package com.all.design23.n07_decorator;

import org.junit.Test;

public class DecoratorTest {
    @Test
    public void test() {
        Decorator decorator = new Decorator(new Source());
        decorator.method();
    }

}