package com.util;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class HttpClientUtil {

    public static String httpPost(String url, Map<String, String> params) {
        String result = "";
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-type", "application/x-www-form-urlencoded");
        CloseableHttpClient client = HttpClientBuilder.create().build();
        List<NameValuePair> names = new ArrayList<>();
        if (!CollectionUtils.isEmpty(params)) {
            params.forEach((k, v) -> names.add(new BasicNameValuePair(k, v)));
        }
        try {
            post.setEntity(new UrlEncodedFormEntity(names, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        post.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        try {
            CloseableHttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String httpGet(String url) {
        CloseableHttpClient client = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet(url);
        try {
            CloseableHttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "null";
    }
}
