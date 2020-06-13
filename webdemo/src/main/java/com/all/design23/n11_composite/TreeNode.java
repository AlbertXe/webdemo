package com.all.design23.n11_composite;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Enumeration;
import java.util.Vector;

@Setter
@Getter
@Slf4j
public class TreeNode {
    private String name;
    private TreeNode parent;
    private Vector<TreeNode> children = new Vector<>();

    public TreeNode(String name) {
        this.name = name;
    }

    public void add(TreeNode treeNode) {
        children.add(treeNode);
    }

    public void remove(TreeNode treeNode) {
        children.remove(treeNode);
    }

    public Enumeration<TreeNode> get() {
        return children.elements();
    }


}
