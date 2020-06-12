package com.all.design23.n23_interpreter;

public class Plus implements Interpreter {
    @Override
    public int interprete(Context context) {
        return context.getNum1() + context.getNum2();
    }
}
