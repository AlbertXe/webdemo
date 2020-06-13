package com.all.design23.n08_proxy;

import com.all.design23.n07_decorator.Source;
import org.junit.Test;

public class ProxyTest {

    @Test
    public void test() {
        Proxy proxy = new Proxy(new Source());
        proxy.method();
    }

}