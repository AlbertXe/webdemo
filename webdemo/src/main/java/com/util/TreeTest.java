package com.util;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 86150
 * TreeTest
 * 2021/3/31 21:35
 */
public class TreeTest {
    Atree root = new Atree("0","0");
    @Before
    public void build() {

        Atree c1 = new Atree("1","1");
        Atree c2 = new Atree("2","2");
        Atree c3 = new Atree("3","3");
        Atree c12 = new Atree("12","12");
        Atree c13 = new Atree("13","13");
        Atree c14 = new Atree("14","14");
        Atree c06 = new Atree("06","06");

        Atree c22 = new Atree("22","22");
        Atree c21 = new Atree("21","21");
        Atree c23 = new Atree("23","22");
        Atree c24 = new Atree("24","24");

        Atree c33 = new Atree("33","33");
        Atree c34 = new Atree("34","34");
        Atree c35 = new Atree("35","35");

        root.addChild(c1);
        root.addChild(c2);
        root.addChild(c3);

        c1.addChild(c12);
        c1.addChild(c13);
        c13.addChild(c14);
        c13.addChild(c06);

        c2.addChild(c22);
        c2.addChild(c21);
        c22.addChild(c23);
        c22.addChild(c24);

        c3.addChild(c33);
        c33.addChild(c34);
        c33.addChild(c35);
    }

    @Test
    public void test() {
        List<Atree> list = new ArrayList<>();
        TreeUtil.getAllLeaf(root, list);
        System.out.println(list);
        System.out.println(root);

        TreeUtil.flag(list, "1");
        System.out.println(root);

        TreeUtil.divide(root);
        System.out.println(root);

    }


}
