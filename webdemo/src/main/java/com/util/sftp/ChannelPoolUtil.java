package com.util.sftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ChannelPoolUtil {
    public static Logger logger = LoggerFactory.getLogger(ChannelPoolUtil.class);
    private int channelNum = 10;
    // 正在执行状态的channel池
    private Map<Integer, Channel> runningChannelPool = new HashMap<Integer, Channel>(
            channelNum);
    // 空闲状态的channel池
    private Map<Integer, Channel> sleepChannelPool = new HashMap<Integer, Channel>(
            channelNum);

    // 新建读写锁
    // private static ReentrantReadWriteLock lock = new
    // ReentrantReadWriteLock();

    /**
     * @return List<Object>
     * @throws JSchException
     * @throws
     * @description 获取channelList
     */
    public synchronized Channel getChannel(SftpInfo sftpInfo)
            throws JSchException {
        Channel channel = null;
        logger.info("当前空闲池里有：" + sleepChannelPool.size() + "个channel");
        // 判断SleepPool中是否有channel
        if (sleepChannelPool.size() == 0) {
            // 判断已连接channel总数是否大于指定数量，否则创建新的channel,并返回
            if ((sleepChannelPool.size() + runningChannelPool.size()) < channelNum) {
                channel = SftpService.getChannel(sftpInfo.getIp(),
                        sftpInfo.getPort(), sftpInfo.getUsername(),
                        sftpInfo.getPwd());
                logger.info("创建了一个新的channel,ID为："
                        + String.valueOf(channel.getId()));
                runningChannelPool.put(channel.getId(), channel);
                logger.info("新channel已塞入到：runningChannelPool");
                return channel;
            } else {
                logger.info("worning!!暂时无空闲的channel");
                return null;
            }
        } else {
            // 从sleepPool中随机获取一个channel
            Integer[] keys = sleepChannelPool.keySet().toArray(new Integer[0]);
            Integer channelId = keys[0];
            channel = sleepChannelPool.get(channelId);
            System.out.println("从池中获取到channel,ID为：" + channelId);
            // 检查channel是否关闭，重连
            if (channel.isClosed()) {
                Session session;
                try {
                    session = channel.getSession();
                    if (!session.isConnected()) {
                        session.connect();
                    }
                    if (!channel.isConnected()) {
                        channel.connect();
                    }
                } catch (JSchException e) {
                    logger.info("", e);
                    logger.info("channel已关闭且重连出错！将其移出池并关闭！");
                    sleepChannelPool.remove(channelId);
                    channel.getSession().disconnect();
                    e.printStackTrace();
                    return null;
                }
            }
            // 将channel从sleepPool挪入runningPool
            sleepChannelPool.remove(channelId);
            runningChannelPool.put(channelId, channel);
            logger.info("获取channel成功！并完成sleepPool-->runningPool");
        }
        return channel;
    }

    /**
     * @return boolean
     * @throws
     * @description 将使用完的channel放回sleepPool
     */
    public synchronized boolean giveChannel(Channel channel) {
        runningChannelPool.remove(channel.getId());
        sleepChannelPool.put(channel.getId(), channel);
        logger.info("归还channel成功！并完成runningPool-->sleepPool");
        return true;
    }

    public ChannelPoolUtil() {
        super();
    }

    public ChannelPoolUtil(int channelNum,
                           Map<Integer, Channel> runningChannelPool,
                           Map<Integer, Channel> sleepChannelPool) {
        super();
        this.channelNum = channelNum;
        this.runningChannelPool = runningChannelPool;
        this.sleepChannelPool = sleepChannelPool;
    }

    public int getChannelNum() {
        return channelNum;
    }

    public void setChannelNum(int channelNum) {
        this.channelNum = channelNum;
    }

    public Map<Integer, Channel> getRunningChannelPool() {
        return runningChannelPool;
    }

    public void setRunningChannelPool(Map<Integer, Channel> runningChannelPool) {
        this.runningChannelPool = runningChannelPool;
    }

    public Map<Integer, Channel> getSleepChannelPool() {
        return sleepChannelPool;
    }

    public void setSleepChannelPool(Map<Integer, Channel> sleepChannelPool) {
        this.sleepChannelPool = sleepChannelPool;
    }

}
