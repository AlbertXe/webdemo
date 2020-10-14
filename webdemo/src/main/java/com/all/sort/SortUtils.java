package com.all.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 排序工具
 * @author: AlbertXe
 * @create: 2020-10-13 14:18
 */
public class SortUtils {

    /**
     * 桶排序
     *
     * @param ss
     * @return
     */
    public static int[] bucketSort(int[] ss) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < ss.length; i++) {
            max = Integer.max(max, ss[i]);
            min = Integer.min(min, ss[i]);
        }
        // 1 找出最大最小 2 分桶 3 桶排序 4 合并
        int bucket = (max - min) / ss.length + 1;
        List<List<Integer>> bucketArr = new ArrayList<>(bucket);
        for (int i = 0; i < bucket; i++) {
            List<Integer> list = new ArrayList<>();
            bucketArr.add(list);
        }
        for (int i = 0; i < ss.length; i++) {
            int num = (ss[i] - min) / ss.length;
            bucketArr.get(num).add(ss[i]);
        }

        for (int i = 0; i < bucketArr.size(); i++) {
            Collections.sort(bucketArr.get(i));
            System.out.println(bucketArr.get(i));
        }
        // flatMap的说明：这个在这里的主要作用是对流进行扁平化
        List<Integer> result = bucketArr.stream().flatMap(t -> t.stream()).collect(Collectors.toList());
        List<List<Integer>> result2 = bucketArr.stream().collect(Collectors.toList());

        System.out.println("结果:" + result);
        System.out.println("结果:" + result2);
        return null;
    }

    /**
     * 归并排序
     *
     * @param ss
     * @return
     */
    public static void mergeSort(int[] ss, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        mergeSort(ss, left, mid);
        mergeSort(ss, mid + 1, right);
        merge(ss, left, mid, right);
    }

    private static void merge(int[] ss, int l_left, int mid, int r_right) {
        int[] tempArr = new int[ss.length];
        int r_left = mid + 1;
        int l_right = mid;

        // third记录临时数组的索引
        int third = l_left;

        int temp = l_left;
        while (l_left <= l_right && r_left <= r_right) {
            // 从两个数组中取出最小的放入临时数组
            if (ss[l_left] <= ss[r_left]) {
                tempArr[third++] = ss[l_left++];
            } else {
                tempArr[third++] = ss[r_left++];
            }
        }
        //剩余部分依次放入临沭数组
        while (r_left <= r_right) {
            tempArr[third++] = ss[r_left++];
        }
        while (l_left <= l_right) {
            tempArr[third++] = ss[l_left++];
        }

        while (temp <= r_right) {
            ss[temp] = tempArr[temp++];
        }

    }
}
