package com.all.design23.n21_visitor;


/**
 * 访问者模式  分离对象数据结构和行为
 * 访问者模式的优点是增加操作很容易，因为增加操作意味着增加新的访问者
 */
public interface Visitor {
    void visit(Subject subject);
}
