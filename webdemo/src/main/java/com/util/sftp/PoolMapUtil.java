package com.util.sftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PoolMapUtil {
    private static Map<String, ChannelPoolUtil> poolMap = new HashMap<String, ChannelPoolUtil>();
    public static Logger logger = LoggerFactory.getLogger(PoolMapUtil.class);
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * @param sftpInfo
     * @return Channel
     * @throws JSchException
     * @throws
     * @description 获取一个channel, 并管理poolMap
     */
    public static Channel getChannel(SftpInfo sftpInfo) throws JSchException {
        Channel channel = null;
        ChannelPoolUtil channelPool = null;

        Map<Integer, Channel> runningChannelPool = new HashMap<Integer, Channel>(sftpInfo.getChannelNum());
        //空闲状态的channel池
        Map<Integer, Channel> sleepChannelPool = new HashMap<Integer, Channel>(sftpInfo.getChannelNum());

        // ip+port+user 为 key
        String poolId = sftpInfo.getIp() + sftpInfo.getPort() + sftpInfo.getUsername() + sftpInfo.getPwd();
        // 写锁（不支持同时读/写）
        lock.writeLock().lock();
        // 如果该池不存在
        if (poolMap.get(poolId) == null) {
            // 创建一个新pool
            channelPool = new ChannelPoolUtil(sftpInfo.getChannelNum(), runningChannelPool, sleepChannelPool);
            // 放入poolMap
            poolMap.put(poolId, channelPool);
            logger.info("创建了一个新channel连接池，poolId为：" + poolId);
        } else {
            channelPool = poolMap.get(poolId);
        }
        // 释放读锁
        lock.writeLock().unlock();
        channel = channelPool.getChannel(sftpInfo);

        return channel;
    }

    /**
     * @param sftpInfo
     * @param channel
     * @return boolean
     * @throws
     * @description 归还channel
     */
    public static boolean giveChannel(SftpInfo sftpInfo, Channel channel) {
        String poolId = sftpInfo.getIp() + sftpInfo.getPort() + sftpInfo.getUsername() + sftpInfo.getPwd();
        //获取channelPool
        ChannelPoolUtil channelPool = poolMap.get(poolId);
        channelPool.giveChannel(channel);
        return true;
    }
}