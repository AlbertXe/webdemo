package com.util.sftp2;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SFTPProperties.class)
public class SFTPConfiguration {
    @Bean
    SFTPFactory sftpFactory(SFTPProperties sftpProperties) {
        return new SFTPFactory(sftpProperties);
    }

    @Bean
    SFTPPool sftpPool(SFTPFactory sftpFactory) {
        return new SFTPPool(sftpFactory);
    }

    @Bean
    SFTPHelper sftpHelper(SFTPPool sftpPool) {
        return new SFTPHelper(sftpPool);
    }
}
