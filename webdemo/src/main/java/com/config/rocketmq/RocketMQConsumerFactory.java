package com.config.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-09-11 12:11
 */
@Getter
@Setter
public class RocketMQConsumerFactory {
    private Map<String, DefaultMQPushConsumer> consumerMap;

    public DefaultMQPushConsumer get(String key) {
        return consumerMap.get(key);
    }

    public void set(String key, DefaultMQPushConsumer consumer) {
        consumerMap.put(key, consumer);
    }

    public List<DefaultMQPushConsumer> getAll() {
        return Lists.newArrayList(consumerMap.values());
    }

    public void start() {
        consumerMap.forEach((k, v) -> {
            try {
                v.start();
            } catch (MQClientException e) {
                e.printStackTrace();
            }
        });
    }

    public void shutdown() {
        consumerMap.forEach((k, v) -> {
            v.shutdown();
        });
    }

}
