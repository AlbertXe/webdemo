package com.all.design23.n06_adapter.cls;

import com.all.design23.n06_adapter.inter.Source1;
import com.all.design23.n06_adapter.inter.Source2;
import com.all.design23.n06_adapter.inter.Sourceable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 适配器模式
 * 目的是消除由于接口不匹配所造成的类的兼容性问题。主要分为三类：类的适配器模式、对象的适配器模式、接口的适配器模式。
 */
@Slf4j
public class Adapter extends Source implements Targetable {

    // 方法一已经适配了

    @Override
    public void method2() {
        log.info("this is method2");
    }

    @Test
    public void test() {
        Targetable adapter = new Adapter();
        adapter.method1();
        adapter.method2();
    }

    @Test
    public void test2() {
        Source source = new Source();
        Targetable adapter = new Adapter2(source);
        adapter.method1();
        adapter.method2();
    }

    @Test
    public void test3() {
        Sourceable s1 = new Source1();
        Sourceable s2 = new Source2();

        s1.method1();
        s2.method2();
    }
}
