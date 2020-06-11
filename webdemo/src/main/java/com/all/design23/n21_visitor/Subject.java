package com.all.design23.n21_visitor;

public interface Subject {
    void accept(Visitor visitor);

    String getSubject();

    String getHate();
}
