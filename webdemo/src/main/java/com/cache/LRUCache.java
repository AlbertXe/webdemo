package com.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: webdemo
 * @description: lru缓存
 * @author: AlbertXe
 * @create: 2020-09-08 16:07
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private int capacity;

    public LRUCache(int initialCapacity) {
        super(initialCapacity, 0.75f, true);
        this.capacity = initialCapacity;
    }

    /**
     * 超过容器大小 删除队列头部数据
     *
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(3);
        for (int i = 0; i < 10; i++) {
            cache.put("" + i, i + "");
        }
        cache.put("7", "7");
        System.out.println(cache.toString());
    }
}
