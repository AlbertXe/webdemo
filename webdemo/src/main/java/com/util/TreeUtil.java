package com.util;

import java.util.Iterator;
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

    public static void flag(List<Atree> leafs, String name) {
        for (Atree leaf : leafs) {
            String leafName = leaf.getName();
            if (leafName.contains(name)) {
                leaf.setFlag(true);
                flagParentAndBrother(leaf);
            }
        }
    }

    private static void flagParentAndBrother(Atree leaf) {
        if (leaf.getParent() != null) {
            Atree parent = leaf.getParent();
            setParentFlag(parent, true);
            List<Atree> brothers = parent.getChildes();
            for (Atree brother : brothers) {
                brother.setFlag(true);
            }
            flagParentAndBrother(parent);
        }
    }

    private static void setParentFlag(Atree parent, boolean b) {
        parent.setFlag(b);
        if (parent.getParent() != null) {
            setParentFlag(parent.getParent(), b);
        }
    }

    public static void divide(Atree root) {
        if (!root.isFlag()) {
            root = null;
        }
        List<Atree> childes = root.getChildes();
        Iterator<Atree> iterator = childes.iterator();
        while (iterator.hasNext()) {
            Atree child = iterator.next();
            if (!child.isFlag()) {
                iterator.remove();
            }
        }
        childes.forEach(c -> divide(c));

    }

}
