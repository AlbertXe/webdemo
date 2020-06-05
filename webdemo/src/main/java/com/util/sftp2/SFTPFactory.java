package com.util.sftp2;

import com.jcraft.jsch.Channel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

@Getter
@Setter
public class SFTPFactory extends BasePooledObjectFactory<Channel> {
    private SFTPProperties sftpProperties;

    public SFTPFactory(SFTPProperties sftpProperties) {
        this.sftpProperties = sftpProperties;
    }


    @Override
    public Channel create() throws Exception {
        //TODO 创建sftp channel

        return null;
    }

    @Override
    public PooledObject<Channel> wrap(Channel channel) {
        return new DefaultPooledObject<>(channel);
    }

    @Override
    public void destroyObject(PooledObject<Channel> p) throws Exception {
        //TODO 失去连接
        Channel channel = p.getObject();
        channel.disconnect();
    }

}
