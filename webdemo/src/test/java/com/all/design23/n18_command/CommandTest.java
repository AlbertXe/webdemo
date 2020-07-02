package com.all.design23.n18_command;

import org.junit.Test;

public class CommandTest {
    @Test
    public void test() {
        Command command = new MyCommand(new Receiver());

        Invoker invoker = new Invoker(command);
        invoker.action();
    }

}