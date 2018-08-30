package org.alittlebitch.rocketmq.pool;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ShawnShoper
 * @date 2018/8/30 16:58
 */
public class MQClientPoolFactory {
    private Map<String, DefaultMQPushConsumer> mqClientPool = new ConcurrentHashMap<>();


}
