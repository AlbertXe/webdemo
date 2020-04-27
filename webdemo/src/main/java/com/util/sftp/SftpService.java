package com.util.sftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class SftpService {

    public static Logger logger = LoggerFactory.getLogger(SftpService.class);
    //创建一个map用于存放channel对象
    private static final Map<String, Channel> SFTP_CHANNEL_POOL = new HashMap<String, Channel>();
    //使用池的ip和端口
    //private static List<String> ipList = new ArrayList<String>(Arrays.asList("172.20.10.3"));

    /**
     * @param ip
     * @param port
     * @param user
     * @param pwd
     * @return ChannelSftp
     * @throws JSchException
     * @throws
     * @description 获取sftp连接
     */
    public Channel getChannelSftp(String ip, String port, String user,
                                  String pwd) throws JSchException {
        Session session = null;
        Channel channel = null;

        int sftpPort = Integer.parseInt(port);
        String key = ip + "," + port + "," + user + "," + pwd;

        if (SFTP_CHANNEL_POOL.get(key) == null) {
            JSch jsch = new JSch();
            session = jsch.getSession(user, ip, sftpPort);
            session.setPassword(pwd);
            // 设置是否需要确认连接为no
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications",
                    "publickey,keyboard-interactive,password");
            session.connect(60000);

            channel = session.openChannel("sftp");
            channel.getId();
            channel.connect();
            SFTP_CHANNEL_POOL.put(key, channel);
            logger.info("该channel是新连接的，ID为：" + channel.getId());

        } else {

            channel = SFTP_CHANNEL_POOL.get(key);
            session = channel.getSession();
            if (!session.isConnected()) {
                session.connect();
            }
            if (!channel.isConnected()) {
                channel.connect();
            }
            logger.info("该channel从map中获取的，ID为：" + channel.getId());
        }
        // channelSftp = (ChannelSftp) channel;
        /*
         * channelSftp.disconnect(); channel.disconnect(); session.disconnect();
         */
        // System.out.println("-------channelSftp已连接");
        return channel;

    }

    public static Channel getChannel(String ip, String port, String user,
                                     String pwd) throws JSchException {
        Session session = null;
        Channel channel = null;
        //ChannelSftp channelSftp = null;

        int sftpPort = Integer.parseInt(port);

        JSch jsch = new JSch();
        session = jsch.getSession(user, ip, sftpPort);
        session.setPassword(pwd);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setConfig("PreferredAuthentications",
                "publickey,keyboard-interactive,password");
        session.connect(60000);

        channel = session.openChannel("sftp");
        channel.connect();

        return channel;

    }

    /**
     * 下载文件-sftp协议.
     *
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     * @param sftp         sftp连接
     * @return 文件
     * @throws Exception 异常
     */
    public static boolean download(final String downloadFile, final String saveFile, final ChannelSftp sftp)
            throws Exception {
        FileOutputStream os = null;
        File file = new File(saveFile);
        try {
            if (!file.exists()) {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                file.createNewFile();
            }
            os = new FileOutputStream(file);
            List<String> list = formatPath(downloadFile);
            sftp.get(list.get(0) + list.get(1), os);
        } catch (Exception e) {
            logger.info("", e);
            throw e;
        } finally {
            os.close();
        }
        return true;
    }

    /**
     * 上传文件-sftp协议.
     *
     * @param srcFile  源文件
     * @param dir      保存路径
     * @param fileName 保存文件名
     * @param sftp     sftp连接
     * @throws Exception 异常
     */
    private static void uploadFile(final String srcFile, final String dir, final String fileName, final ChannelSftp sftp)
            throws SftpException {
        mkdir(dir, sftp);
        sftp.cd(dir);
        sftp.put(srcFile, fileName);
    }


    /**
     * 上传文件-sftp协议.
     *
     * @param srcFile 源文件路径，/xxx/xx.yy 或 x:/xxx/xxx.yy
     * @param sftp    sftp连接
     * @return 上传成功与否
     * @throws SftpException 异常 localFile directoryFile
     */
    public static boolean uploadFile(final String srcFile, final String directoryFile, final ChannelSftp sftp) throws SftpException {
        File file = new File(srcFile);
        if (file.exists()) {
            List<String> list = formatPath(directoryFile);
            uploadFile(srcFile, list.get(0), list.get(1), sftp);
            return true;
        }
        return false;
    }


    /**
     * 根据路径创建文件夹.
     *
     * @param dir  路径 必须是 /xxx/xxx/ 不能就单独一个/
     * @param sftp sftp连接
     * @throws SftpException 异常
     */
    @SuppressWarnings("null")
    public static boolean mkdir(final String dir, final ChannelSftp sftp) throws SftpException {
        if (dir == null && dir.length() == 0)
            return false;
        String md = dir.replaceAll("\\\\", "/");
        if (md.indexOf("/") != 0 || md.length() == 1)
            return false;
        return mkdirs(md, sftp);
    }

    /**
     * 递归创建文件夹.
     *
     * @param dir  路径
     * @param sftp sftp连接
     * @return 是否创建成功
     * @throws SftpException 异常
     */
    private static boolean mkdirs(final String dir, final ChannelSftp sftp) throws SftpException {
        String dirs = dir.substring(1, dir.length() - 1);
        String[] dirArr = dirs.split("/");
        String base = "";
        for (String d : dirArr) {
            base += "/" + d;
            if (dirExist(base + "/", sftp)) {
                continue;
            } else {
                sftp.mkdir(base + "/");
            }
        }
        return true;
    }

    /**
     * 判断文件夹是否存在.
     *
     * @param dir  文件夹路径， /xxx/xxx/
     * @param sftp sftp协议
     * @return 是否存在
     */
    private static boolean dirExist(final String dir, final ChannelSftp sftp) {
        try {
            Vector<?> vector = sftp.ls(dir);
            if (null == vector)
                return false;
            else
                return true;
        } catch (SftpException e) {
            return false;
        }
    }


    /**
     * 格式化路径.
     *
     * @param srcPath 原路径. /xxx/xxx/xxx.yyy 或 X:/xxx/xxx/xxx.yy
     * @return list, 第一个是路径（/xxx/xxx/）,第二个是文件名（xxx.yy）
     */
    public static List<String> formatPath(final String srcPath) {
        List<String> list = new ArrayList<String>(2);
        String repSrc = srcPath.replaceAll("\\\\", "/");
        int firstP = repSrc.indexOf("/");
        int lastP = repSrc.lastIndexOf("/");
        String fileName = lastP + 1 == repSrc.length() ? "" : repSrc.substring(lastP + 1);
        String dir = firstP == -1 ? "" : repSrc.substring(firstP, lastP);
        dir = (dir.length() == 1 ? dir : (dir + "/"));
        list.add(dir);
        list.add(fileName);
        return list;
    }

    @SuppressWarnings("unused")
    private static void exit(final ChannelSftp sftp) {
        sftp.exit();
    }

    /**
     * @return ChannelSftp
     * @throws JSchException
     * @throws
     * @description 获取一个channel
     */
    public Channel getChannelFromPool(SftpInfo sftpInfo)
            throws JSchException {
        Channel channel = null;

        if (sftpInfo.getIsPool().equals("Y")) {
            logger.info("将使用channel连接池");
            channel = PoolMapUtil.getChannel(sftpInfo);
        } else {
            try {
                logger.info("未使用channel连接池");
                channel = getChannelSftp(sftpInfo.getIp(), sftpInfo.getPort(),
                        sftpInfo.getUsername(), sftpInfo.getPwd());
            } catch (JSchException e) {
                logger.info("", e);
                e.printStackTrace();
            }
        }
        return channel;
    }

    /**
     * @return boolean
     * @throws
     * @description SFTP操作主方法
     */
    public boolean SFTPOper(SftpInfo sftpInfo) {
        Channel channel = null;
        try {
            // 获取channel
            channel = getChannelFromPool(sftpInfo);
            if (channel == null) {
                // 现在不存在可用的channel
                logger.info("没有获取到channel");
                return false;
            }
            logger.info("获取到channel" + String.valueOf(channel.getId()));
            // 获取ChannelSftp对象进行sftp操作
            ChannelSftp sftp = (ChannelSftp) channel;
            // 文件上传
            if (sftpInfo.getOperFlag().equals("1")) {
                logger.info("开始上传文件：本地路径为：" + sftpInfo.getLocalFile() + ",远程文件路径为：" + sftpInfo.getDirectoryFile());
                return uploadFile(sftpInfo.getLocalFile(), sftpInfo.getDirectoryFile(), sftp);

            }
            // 文件下载
            else if (sftpInfo.getOperFlag().equals("2")) {
                logger.info("开始下载文件：远程路径为：" + sftpInfo.getDirectoryFile() + ",本地文件路径为：" + sftpInfo.getLocalFile());
                return download(sftpInfo.getDirectoryFile(), sftpInfo.getLocalFile(), sftp);
            } else {
                logger.info("不支持该操作标识！");
                return false;
            }
        } catch (Exception e) {
            logger.info("", e);
            e.printStackTrace();
            // 文件操作失败
        } finally {
            //判断是否需要归还channel
            if (sftpInfo.getIsPool().equals("Y")) {
                if (channel != null) {
                    // 归还channel到sleepPool
                    logger.info("将channel归还到sleepPool");
                    PoolMapUtil.giveChannel(sftpInfo, channel);
                }
            }
        }
        return false;
    }

    public SftpService() {
        super();
    }
}