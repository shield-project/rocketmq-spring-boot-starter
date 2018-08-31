package org.alittlebitch.rocketmq.pool;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ShawnShoper
 * @date 2018/8/30 16:58
 */
public class MQClientPoolFactory {
    //instance id for key,DefaultMQPushConsumer for value
    private Map<String, DefaultMQPushConsumer> mqClientPool = new ConcurrentHashMap<>();

    public DefaultMQPushConsumer borrow(String instanceId) {
        if (Objects.isNull(instanceId) || instanceId.trim().isEmpty())
            throw new NullPointerException("instanceId can not be null or empty.");

        return new DefaultMQPushConsumer();
    }

}
