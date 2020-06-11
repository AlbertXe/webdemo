package com.all.design23.n21_visitor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyVisitor implements Visitor {
    @Override
    public void visit(Subject subject) {
        log.info("visit " + subject.getSubject());
    }
}
