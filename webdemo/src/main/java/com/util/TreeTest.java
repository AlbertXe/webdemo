package com.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 86150
 * TreeTest
 * 2021/3/31 21:35
 */
public class TreeTest {

    @Test
    public void test() {
        Atree root = new Atree("0","0");
        Atree c1 = new Atree("1","1");
        Atree c2 = new Atree("2","2");
        Atree c12 = new Atree("12","12");
        Atree c22 = new Atree("22","22");
        root.addChild(c1);
        root.addChild(c2);
        c1.addChild(c12);
        c2.addChild(c22);

        List<Atree> list = new ArrayList<>();
        TreeUtil.getAllLeaf(root, list);
        System.out.println(list);
        System.out.println(root);
    }
}
