package com.all.design23.n20_status;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 状态模式 不同状态调用不同的方法
 */
@Getter
@Setter
@Slf4j
public class Status {
    private String value;

    public Status(String value) {
        this.value = value;
    }

    public void method1() {
        log.info("method1");
    }

    public void method2() {
        log.info("method2");
    }

}
