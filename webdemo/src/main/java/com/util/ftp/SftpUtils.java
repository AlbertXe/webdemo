package com.util.ftp;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author Administrator
 * @date 2019-11-20 14:41
 */
public class SftpUtils {
    private static final Logger logger = LoggerFactory.getLogger(SftpUtils.class);

    private static String host = "127.0.0.1";
    private static int port = 22;
    private static String username = "";
    private static String password = "";

    /**
     * 建立连接，放回SFTP
     *
     * @param s
     */
    public static void connect(SFTP s) {
        JSch jSch = new JSch();
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        try {
            session = jSch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");//不验证host
            session.connect();
        } catch (JSchException e) {
            logger.error("连接服务器失败,请检查主机[" + host + "],端口[" + port
                    + "],用户名[" + username + "],端口[" + port
                    + "]是否正确,以上信息正确的情况下请检查网络连接是否正常或者请求被防火墙拒绝.");
        }

        try {
            channel = session.openChannel("sftp");
            channel.connect();
        } catch (JSchException e) {
            logger.error("连接服务器失败,请检查主机[" + host + "],端口[" + port
                    + "],用户名[" + username + "],端口[" + port
                    + "]是否正确,以上信息正确的情况下请检查网络连接是否正常或者请求被防火墙拒绝.");
        }
        channelSftp = (ChannelSftp) channel;

        s.setSession(session);
        s.setChannel(channel);
        s.setSftp(channelSftp);
    }

    /**
     * 关闭连接
     *
     * @param s
     */
    public static void disConnect(SFTP s) {
        ChannelSftp sftp = s.getSftp();
        Channel channel = s.getChannel();
        Session session = s.getSession();
        if (sftp != null) {
            sftp.disconnect();
            sftp.exit();
            sftp = null;
        }
        if (channel != null) {
            channel.disconnect();
            channel = null;
        }
        if (session != null) {
            session.disconnect();
            session = null;
        }
    }

    /**
     * 上传文件
     *
     * @param localFile 本地文件
     * @param outDir    服务器目录
     */
    public static void upload(String localFile, String outDir) throws FileNotFoundException {
        SFTP s = new SFTP();
        connect(s);
        ChannelSftp sftp = s.getSftp();
        try {
            sftp.cd(outDir);//进入目录
        } catch (SftpException e) {
            if (e.id == sftp.SSH_FX_NO_SUCH_FILE) {
                try {
                    sftp.mkdir(outDir);//创建并且 进入目录
                    sftp.cd(outDir);
                } catch (SftpException e1) {
                    e1.printStackTrace();
                }

            }
            e.printStackTrace();
        }
        File file = new File(localFile);
        try {
            //上传文件
            sftp.put(new FileInputStream(file), file.getName());

        } catch (SftpException e) {

        } finally {
            disConnect(s);
        }

    }
}
