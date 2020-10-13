package com.all.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        return null;
    }
}
