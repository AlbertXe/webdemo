package com.all.sort;

import org.junit.Test;

/**
 * @description: 排序测试
 * @author: AlbertXe
 * @create: 2020-10-13 14:31
 */
public class SortUtilsTest {

    private int[] ss = new int[]{101, 4, 7, 5, 3, 2, 9, 7, 4};

    @Test
    public void bucketSort() {
        SortUtils.bucketSort(ss);
    }
}
