package com.util.sftp2;

import com.jcraft.jsch.Channel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sftp")
@Slf4j
@Getter
@Setter
public class SFTPProperties {
    private String host;
    private int port = 22;
    private String username;
    private String password;
    private Pool pool = new Pool();

    @Getter
    @Setter
    public static class Pool extends GenericObjectPoolConfig<Channel> {
        private int maxTotal = DEFAULT_MAX_TOTAL;
        private int maxIdel = DEFAULT_MAX_IDLE;
        private int minIdel = DEFAULT_MIN_IDLE;

    }


}
