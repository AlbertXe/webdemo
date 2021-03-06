package com.all.sort;

import org.junit.After;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @description: 排序测试
 * @author: AlbertXe
 * @create: 2020-10-13 14:31
 */
public class SortUtilsTest {

    private int[] ss = new int[]{101, 4, 7, 5, 3, 2, 9, 7, 4};

    @After
    public void after() {
        String result = Arrays.stream(ss).mapToObj(s -> s + "").collect(Collectors.joining(","));
        System.out.println(result);
    }

    @Test
    public void bucketSort() {
        SortUtils.bucketSort(ss);
    }

    @Test
    public void mergeSort() {
        SortUtils.mergeSort(ss, 0, ss.length - 1);
    }

    @Test
    public void baseSort() {
        SortUtils.baseSort(ss);
    }
}
