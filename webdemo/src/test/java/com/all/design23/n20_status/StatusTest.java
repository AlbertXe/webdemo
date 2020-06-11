package com.all.design23.n20_status;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class StatusTest {
    @Test
    public void test() {
        Status status = new Status("method1");
        Context context = new Context(status);
        log.info("第一张模式");
        context.method();

        log.info("第二张模式");
        status.setValue("method2");
        context.method();


    }
}