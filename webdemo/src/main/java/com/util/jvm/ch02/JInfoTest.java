package com.util.jvm.ch02;

import org.junit.Test;

import java.util.Map;

/**
 * @author AlbertXe
 * @date 2019-12-01 14:35
 */
public class JInfoTest {

    @Test
    public void testJinfo(){
        while (true) {
            byte[] b = null;
            for (int i = 0; i < 10; i++) {
                b = new byte[1024 * 1024];
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Map<Thread, StackTraceElement[]> threadMap = Thread.getAllStackTraces();
            for (Map.Entry<Thread, StackTraceElement[]> entry : threadMap.entrySet()) {
                Thread t = entry.getKey();
                StackTraceElement[] value = entry.getValue();
                System.out.println(t.getName() + "-" + t.getId());
                for (StackTraceElement stackTraceElement : value) {//打印线程的栈信息
                    System.out.println(stackTraceElement);
                }
            }
        }
    }
}
