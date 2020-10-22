package com.util.file;

import org.junit.Test;

import java.io.File;

/**
 * @description: 大文件去重测试
 * @author: AlbertXe
 * @create: 2020-10-21 14:06
 */
public class BigFileDistinctUtilTest {

    @Test
    public void test() {
        File file = new File("d:/a.txt");
        BigFileDistinctUtil build = BigFileDistinctUtil.builder().function(t -> t).build();
        build.uniq(file);
    }

}
