package com.util;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AlbertXe
 * @date 2019-11-05 14:45
 */

public class HttpClientUtilTest {
    @Test
    public void test1() {
        Map<String, String> map = new HashMap<>();
        map.put("id", "1");
        HttpClientUtil.httpPost("http://localhost:8080/book", map);
    }

    @Test
    public void test2() {
        HttpClientUtil.httpGet("http://localhost:8080/users");
    }


}
