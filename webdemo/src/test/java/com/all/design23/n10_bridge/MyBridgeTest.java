package com.all.design23.n10_bridge;

import org.junit.Test;

public class MyBridgeTest {
    @Test
    public void test() {
        MyBridge bridge = new MyBridge();
        bridge.setSourceable(new Source1());
        bridge.method();
        bridge.setSourceable(new Source2());
        bridge.method();

    }

}