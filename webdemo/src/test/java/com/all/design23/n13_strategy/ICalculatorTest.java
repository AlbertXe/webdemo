package com.all.design23.n13_strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ICalculatorTest {
    @Test
    public void test() {
        Plus plus = new Plus();
        int i = plus.calculate("2+3");
        log.info("result = " + i);
    }

    @Test
    public void test1() {
        Multiply multiply = new Multiply();
        int i = multiply.calculate("2*3");
        log.info("result = " + i);
    }

}