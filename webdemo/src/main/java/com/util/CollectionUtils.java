package com.util;

import com.pojo.User;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 集合工具类
 *
 * @author AlbertXe
 * @date 2019-11-12 14:13
 */
public class CollectionUtils {
    private List list;

    /**
     * 集合去重
     *
     * @param list
     * @param <T>
     * @return
     */
    public List distinct(List list) {

//        list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(TreeSet::new), ArrayIterator::new));
        //根据id去重
        //会留下集合前面的数据
        List result = (List) list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                () -> new TreeSet(Comparator.comparing(User::getId))
        ), ArrayList::new));
        return result;
    }

    @Before
    public void before() {
        list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i + "");
            user.setName("user1");
            list.add(user);
        }
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i + "");
            user.setName("user");
            list.add(user);
        }

    }

    @Test
    public void test3() {
        List distinct = distinct(list);
        System.out.println(distinct);

    }

    /**
     * 将map 转 list
     *
     * @param map
     * @return
     */
    public static List toList(Map map) {
//        List<Object> list = (List<Object>) map.values().stream().collect(Collectors.toCollection(ArrayList::new));
        List<Object> list = (List<Object>) map.values().stream().collect(Collectors.toList());
        return list;
    }

    /**
     * 拼接字符串
     *
     * @param list
     * @return
     */
    public static String join(List list) {
        return (String) list.stream().collect(Collectors.joining("|", "start-", "-end"));
    }


    @Test
    public void test1() {
        List<String> list = Arrays.asList("1", "2", "3", "4", "5");
        String join = join(list);
        System.out.println(join);
    }

    @Test
    public void test2() {
        List<String> list = Arrays.asList("1", "2", "3", "4", "5");
        //对收集的结果在做一次操作
        String s1 = list.stream().collect(Collectors.collectingAndThen(Collectors.joining("|"), s -> s + "|d"));
        System.out.println(s1);
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("1", "1");
        map.put("2", "2");
        List list = toList(map);
        System.out.println(list);
    }

    /**
     * 对集合排序
     *
     * @param list
     */
    public static void sort(List<User> list) {
        list.sort(Comparator.comparingInt((User u) -> Integer.valueOf(u.getId())).reversed());
    }
}
