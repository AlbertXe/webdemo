package com.all.design23.n14_template;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class TemplateTest {
    @Test
    public void test() {
        Plus plus = new Plus();
        int i = plus.calculate("4+3", "\\+");
        log.info("result = " + i);
    }

}