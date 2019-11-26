package com.util;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * CEPH工具 由于服务器原因不能成功
 *
 * @author AlbertXe
 * @date 2019-11-18 16:34
 */
public class CephUtils {
    private final String key1 = "WSGGYRNF5D76PDHMGZJ2";
    private final String key2 = "POHs059Xddh4u49saxry072bQdwtCfScale5Nkot";
    private final String endPort = "http://29.2.58.15/";

    private final String myselfbucket = "myselfbucket";

    private AmazonS3 client;

    @Before
    @Test
    public void init() {
        if (client == null) {
            ClientConfiguration configuration = new ClientConfiguration();
            configuration.setProtocol(Protocol.HTTP);
            BasicAWSCredentials credentials = new BasicAWSCredentials(key1, key2);
            client = new AmazonS3Client(credentials, configuration);
            client.setEndpoint(endPort);
        }
    }

    @Test
    public void getObject() {
        GetObjectRequest request = new GetObjectRequest(myselfbucket, "hello.txt");
        client.getObject(request, new File("D:\\hello.txt"));
    }


}
