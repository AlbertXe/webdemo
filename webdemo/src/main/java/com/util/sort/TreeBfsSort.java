package com.util.sort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 树 广度优先
 * 需要用到队列（Queue）来存储节点对象,队列的特点就是先进先出
 * AlbertXe
 * 2019/11/27 23:50
 */
public class TreeBfsSort {

    public static ArrayList<Integer> sort(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            //将每个节点的左右节点依次放入队列
            TreeNode tree = queue.poll(); //无则空
            if (tree.left!=null) {
                queue.offer(tree.left); //满则false
            }
            if (tree.right != null) {
                queue.offer(tree.right);
            }
            result.add(tree.value);
        }
        return result;
    }

}
