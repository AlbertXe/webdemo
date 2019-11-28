package com.util.sort;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 树 深度优先
 * 深度优先遍历各个节点，需要使用到栈（Stack）这种数据结构。stack的特点是是先进后出。整个遍历过程如下：
 *先往栈中压入右节点，再压左节点，这样出栈就是先左节点后右节点了。
 * AlbertXe
 * 2019/11/28 0:08
 */
public class TreeDfsSort {

    /**
     * 非递归
     * @param root
     * @return
     */
    public static ArrayList sort(TreeNode root) {
        ArrayList<Integer> result = new ArrayList();
        if (root == null) {
            return result;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode tree = stack.pop();
            if (tree.right != null) {
                stack.push(tree.right);
            }
            if (tree.left != null) {
                stack.push(tree.left);
            }
            result.add(tree.value);
        }
        return result;
    }

    /**
     * 深度  递归搜索
     * @param root
     */
    public void sort2(TreeNode root) {
        if (root != null) {
            System.out.println(root.value);
            sort2(root.left);
            sort2(root.right);
        }

    }
}
