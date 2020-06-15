package com.all.design23.n13_strategy;

public abstract class AbstractCalculator implements ICalculator {
    public int[] split(String exp, String opt) {
        String[] ss = exp.split(opt);
        return new int[]{Integer.parseInt(ss[0]), Integer.parseInt(ss[1])};
    }
}
