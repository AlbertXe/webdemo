package com.all.design23.n21_visitor;

public class MySubject implements Subject {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String getSubject() {
        return "love";
    }

    @Override
    public String getHate() {
        return "hate";
    }
}
