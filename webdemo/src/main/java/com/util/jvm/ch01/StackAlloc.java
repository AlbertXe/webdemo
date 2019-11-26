package com.util.jvm.ch01;

/**
 * -server -Xms10m -Xmx10m -XX:+PrintGC -XX:+DoEscapeAnalysis -XX:+EliminateAllocations -XX:-UseTLAB
 * -Xms 最小  -Xmx最大堆   -XX:+DoEscapeAnalysis 逃逸分析
 * -XX:+EliminateAllocations 标量替换
 * -XX:-UseTLAB   为每个线程预备一块内存 。 避免多个线程竞争一个内存
 *
 * *****栈上分配  -server  逃逸分析   标量替换都要开启   关闭开启差了10倍
 * @author AlbertXe
 * @date 2019-11-24 16:41
 */
public class StackAlloc {
    public static class User {
        private int id = 0;
        private String name = "";
    }

    public static void alloc() {
        User user = new User();
        user.id = 5;
        user.name = "albert";
    }

    public static void main(String[] args) {
        long s = System.currentTimeMillis();

        for (int i = 0; i < 100000000; i++) {
            alloc();
        }
        long e = System.currentTimeMillis();
        System.out.println((e-s)+"ms");
        
    }
}
