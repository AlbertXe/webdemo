package com.config.rocketmq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-09-11 13:36
 */
@Configuration
@ConfigurationProperties(prefix = "mq")
//@ConditionalOnProperty(prefix = "mq",name = "enabled-consumer",value = "false",havingValue = "true")
public class RocketMQConsumerProperties {
//    private List<Consumer> consumer;


}
