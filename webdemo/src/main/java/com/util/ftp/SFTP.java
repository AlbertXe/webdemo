package com.util.ftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;

/**
 * JSCH实现SFTP文件管理（上传、下载等）
 * @author AlbertXe
 * @date 2019-11-20 14:34
 */
public class SFTP {
    /**
     * 会话
     */
    private Session session;
    /**
     * 通道
     */
    private Channel channel;
    /**
     * sftp实际操作
     */
    private ChannelSftp sftp;


    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public ChannelSftp getSftp() {
        return sftp;
    }

    public void setSftp(ChannelSftp sftp) {
        this.sftp = sftp;
    }
}
