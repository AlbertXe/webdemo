package com.util.sftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.junit.Test;

public class SFTPTest {
    //多线程
    @Test
    public void test() {
        for (int i = 0; i < 100; i++) {
            final int j = i;
            Thread thread = new Thread(() -> {
                SftpInfo sftpInfo = new SftpInfo();
                sftpInfo.setIp("172.31.28.116");
                sftpInfo.setPort("22");
                sftpInfo.setUsername("xhwsftp");
                sftpInfo.setPwd("root");
                sftpInfo.setChannelNum(10);

                try {
                    Channel channel = PoolMapUtil.getChannel(sftpInfo);
                    SftpService.uploadFile("D:\\20200411\\test0.tmp" + j, "/upload", (ChannelSftp) channel);
                } catch (JSchException e) {
                    e.printStackTrace();
                } catch (SftpException e) {
                    e.printStackTrace();
                }
            });
            thread.start();

        }
    }

    @Test
    public void test1() {
        String path = "\\\\";
        String s = path.replaceAll("//+|\\\\+", "/");
        System.out.println(path);
        System.out.println(s);
    }
}
