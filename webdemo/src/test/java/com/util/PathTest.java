package com.util;

import org.junit.Test;

/**
 * 从结果来看【TestMain.class.getResource("/") == t.getClass().getClassLoader().getResource("")】
 */
public class PathTest {

    /**
     * Class.getResource(String path)
     * path不以’/'开头时，默认是从此类所在的包下取资源；
     * path  以’/'开头时，则是从ClassPath根下获取；
     * <p>
     * file:/D:/idea_study/webdemo/target/test-classes/com/util/
     * file:/D:/idea_study/webdemo/target/test-classes/
     */
    @Test
    public void test() {
        System.out.println(PathTest.class.getResource(""));
        System.out.println(PathTest.class.getResource("/"));
    }

    /**
     * Class.getClassLoader（）.getResource(String path)
     * path不能以’/'开头时；
     * path是从ClassPath根下获取；
     * <p>
     * file:/D:/idea_study/webdemo/target/test-classes/
     * null
     */
    @Test
    public void test2() {
        System.out.println(PathTest.class.getClassLoader().getResource(""));
        System.out.println(PathTest.class.getClassLoader().getResource("/"));
    }
}
