package com.all.design23.n20_status;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class Context {
    private Status status;

    public Context(Status status) {
        this.status = status;
    }

    public void method() {
        if (status.getValue().equals("method1")) {
            status.method1();
        } else if (status.getValue().equals("method2")) {
            status.method2();
        }
    }
}
