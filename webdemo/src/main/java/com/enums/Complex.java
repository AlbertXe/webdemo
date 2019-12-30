package com.enums;

/**
 * 实现枚举的多重组合
 * @author AlbertXe
 * @date 2019-12-24 12:26
 */
public class Complex {
    private static int a = 0x001;
    private static int b = 0x002;
    private static int c = 0x004;

    private static final int pmask = 0xFFF;
    int permissions;

    public void setPERMISSIONS(int permissions) {
        permissions = (this.permissions & ~pmask) | (permissions & pmask);
        this.permissions = permissions;
    }

    public boolean getA() {
        return (permissions & a) == a;
    }

    public boolean getB() {
        return (permissions & b) == b;
    }

    public boolean getC() {
        return (permissions & c) == c;
    }

    public static void main(String[] args) {
        Complex complex = new Complex();
        complex.setPERMISSIONS(7);

        System.out.println(complex.getA());
        System.out.println(complex.getB());
        System.out.println(complex.getC());
    }


}
