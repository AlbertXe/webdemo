package com.all.design23.n09_facade;

import org.junit.Test;

public class ComputerTest {
    @Test
    public void test() {
        Computer computer = new Computer();
        computer.start();
        computer.shutdown();
    }

}