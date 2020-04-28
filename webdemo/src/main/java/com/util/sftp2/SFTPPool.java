package com.util.sftp2;

import com.jcraft.jsch.Channel;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class SFTPPool {
    private GenericObjectPool<Channel> pool;

    public SFTPPool(SFTPFactory factoory) {
        this.pool = new GenericObjectPool<>(factoory, factoory.getSftpProperties().getPool());
    }

    public Channel borrowObject() {
        try {
            return pool.borrowObject();
        } catch (Exception e) {
            throw new RuntimeException("获得连接失败");
        }
    }


    public void returnObject(Channel channel) {
        if (channel != null) {
            pool.returnObject(channel);
        }
    }
}
