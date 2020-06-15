package com.all.design23.n14_template;

/**
 * 模板方法  父类与子类关系   关系模式（11-2）
 * 一个抽象类中，有一个主方法，再定义1...n个方法，可以是抽象的，
 * 也可以是实际的方法，定义一个类，继承该抽象类，重写抽象方法，通过调用抽象类
 */
public abstract class AbstractCalculator {
    public int[] split(String exp, String opt) {
        String[] ss = exp.split(opt);
        return new int[]{Integer.parseInt(ss[0]), Integer.parseInt(ss[1])};
    }

    public final int calculate(String exp, String opt) {
        int[] ss = split(exp, opt);
        return calculate(ss[0], ss[1]);
    }

    protected abstract int calculate(int s, int s1);
}
