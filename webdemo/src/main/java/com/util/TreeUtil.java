package com.util;

import java.util.List;

/**
 * 86150
 * TreeUtil
 * 2021/3/31 21:26
 */
public class TreeUtil {

    public static void getAllLeaf(Atree root, List<Atree> leafs) {
        if (root.getChildes().isEmpty()) {
            leafs.add(root);
            return;
        }
        for (Atree childe : root.getChildes()) {
            getAllLeaf(childe, leafs);
        }
    }
}
