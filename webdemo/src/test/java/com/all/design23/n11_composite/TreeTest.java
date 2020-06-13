package com.all.design23.n11_composite;

import org.junit.Test;

public class TreeTest {
    @Test
    public void test() {
        Tree tree = new Tree("A");
        TreeNode b = new TreeNode("B");
        TreeNode c = new TreeNode("C");
        b.add(c);

        tree.root.add(b);

        tree.list();

    }

}