package com.util;

import org.junit.Test;

import java.io.IOException;

public class FileUtilsTest {

    @Test
    public void test1() throws IOException {
        FileUtils.listFiles("D:\\idea_study", "glob:**/*.{java,class}");

    }
}
