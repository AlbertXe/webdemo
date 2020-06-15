package com.all.design23.n13_strategy;

public class Multiply extends AbstractCalculator {
    @Override
    public int calculate(String exp) {
        int[] ss = split(exp, "\\*");
        return ss[0] * ss[1];
    }
}
