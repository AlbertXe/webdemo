package com.all.design23.n23_interpreter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Context {
    private int num1;
    private int num2;

    public Context(int num1, int num2) {
        this.num1 = num1;
        this.num2 = num2;
    }
}
