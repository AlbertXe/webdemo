package com.all.design23.n23_interpreter;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class InterpreterTest {

    @Test
    public void test() {
        int sum = new Plus().interprete(new Context(2, 3));

        int result = new Minus().interprete(new Context(sum, 1));

        log.info("result:{}", result);
    }

}