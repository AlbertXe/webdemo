package com.util.ftp;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author AlbertXe
 * @date 2019-11-20 14:41
 */
public class SftpUtils {
    private static final Logger logger = LoggerFactory.getLogger(SftpUtils.class);

    private static String host = "172.31.22.100";
    private static int port = 22;
    private static String username = "xhwsftp";
    private static String password = "root";

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
        System.out.println("开始上传文件");
        SFTP s = new SFTP();
        connect(s);
        ChannelSftp sftp = s.getSftp();

        try {
            TestException.flag = true;
            System.out.println("开始上传文件");
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
            System.out.println("上传文件结束");

        } catch (SftpException e) {
            System.out.println("上传失败");
            e.printStackTrace();
        } finally {
            disConnect(s);
        }

    }

    /**
     *
     * @param dir 带下载目录
     * @param fileName 下载的文件名
     * @param localDir 保存的目录
     */
    public static void download(String dir, String fileName, String localDir) throws SftpException, FileNotFoundException {
        SFTP s = new SFTP();
        connect(s);
        ChannelSftp sftp = s.getSftp();

        sftp.cd(dir);

        File file = new File(localDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileOutputStream os = new FileOutputStream(new File(localDir, fileName));
        sftp.get( fileName, os);
    }

    /**
     * @param dir 目录
     * @param file 文件
     * @throws SftpException
     */
    public static void delete(String dir, String file) throws SftpException {
        SFTP s = new SFTP();
        connect(s);
        ChannelSftp sftp = s.getSftp();
        try {
            sftp.cd(dir);
            sftp.rm(file);
        }finally {
            disConnect(s);
        }
    }

    /**
     *
     * @param dir 目录
     */
    public static List<String> listFiles(String dir) throws SftpException {
        List<String> result = new ArrayList<>();
        SFTP s = new SFTP();
        connect(s);
        ChannelSftp sftp = s.getSftp();
        Vector ls = sftp.ls(dir);
        Iterator iterator = ls.iterator();
        while (iterator.hasNext()) {
            String filename = ((ChannelSftp.LsEntry) iterator.next()).getFilename();
            result.add(filename);
        }
        return result;
    }

    /**
     * 删除目录所有文件
     */
    public static void deleteByDir(String dir) throws SftpException {
        SFTP s = new SFTP();
        connect(s);
        ChannelSftp sftp = s.getSftp();

        sftp.cd(dir);
        List<String> files = listFiles(dir);
        for (String file : files) {
            sftp.rm(file);
        }
    }

    /**
     * 删除目录  必须为空
     * @param dir
     */
    public static void deleteDir(String dir) throws SftpException {
        SFTP s = new SFTP();
        connect(s);
        ChannelSftp sftp = s.getSftp();
        sftp.rmdir(dir);
    }

    /**
     * 创建目录
     * @param dictionary
     * @param dir
     */
    public static void createDir(String dictionary, String dir) throws SftpException {
        SFTP s = new SFTP();
        connect(s);
        ChannelSftp sftp = s.getSftp();
        sftp.cd(dictionary);
        sftp.mkdir(dir);
    }

    /**
     * 重命名
     * @param dir
     * @param oldName
     * @param newName
     */
    public static void rename(String dir, String oldName, String newName) throws SftpException {
        SFTP s = new SFTP();
        connect(s);
        ChannelSftp sftp = s.getSftp();
        sftp.cd(dir);
        sftp.rename(oldName, newName);
    }


}
