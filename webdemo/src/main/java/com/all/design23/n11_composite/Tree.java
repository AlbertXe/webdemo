package com.all.design23.n11_composite;

import lombok.extern.slf4j.Slf4j;

/**
 * 组合模式
 */
@Slf4j
public class Tree {
    TreeNode root;

    public Tree(String name) {
        root = new TreeNode(name);
    }

    public void list() {
        log.info(root.getName());
        for (TreeNode child : root.getChildren()) {
            log.info(child.getName());
        }
    }


}
