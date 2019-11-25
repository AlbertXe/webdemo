package com.util.jvm.ch01;

/**
 * 默认1m   只能捕捉throwable
 * -Xss256k
 */
public class StackOom {
    private static int id = 1;

    public static void main(String[] args) {
        iter();
    }

    public static void iter() {
        id++;
        try {
            iter();
        }catch (Throwable e){
            System.out.println(id);
            e.printStackTrace();
        }
    }
}
