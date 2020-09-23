package com.all.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * @description: java8 并行
 * @author: AlbertXe
 * @create: 2020-09-23 15:50
 */
public class ArrayListSplitorTest {
    AtomicInteger count = new AtomicInteger(0);
    List<String> strList = createList();
    Spliterator spliterator = strList.spliterator();

    /**
     * 多线程计算list中数值的和
     * 测试spliterator遍历
     */
    @Test
    public void mytest() {
        for (int i = 0; i < 4; i++) {
            new MyThread().start();
        }
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结果为：" + count);
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println("线程" + threadName + "开始运行-----");
            spliterator.trySplit().forEachRemaining(new Consumer() {
                @Override
                public void accept(Object o) {
                    if (isInteger((String) o)) {
                        int num = Integer.parseInt(o + "");
                        count.addAndGet(num);
                        System.out.println("数值：" + num + "------" + threadName);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            System.out.println("线程" + threadName + "运行结束-----");
        }
    }

    private List<String> createList() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i % 10 == 0) {
                result.add(i + "");
            } else {
                result.add("aaa");
            }
        }
        return result;
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
