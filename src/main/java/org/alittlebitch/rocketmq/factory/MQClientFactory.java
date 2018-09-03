package org.alittlebitch.rocketmq.factory;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ShawnShoper
 * @date 2018/8/30 16:58
 */
public class MQClientFactory {
    //instance id for key,DefaultMQPushConsumer for value
    protected static Map<String, DefaultMQPushConsumer> mqClients = new ConcurrentHashMap<>();

    protected static DefaultMQPushConsumer get(String instanceId) {
        if (Objects.isNull(instanceId) || instanceId.trim().isEmpty())
            throw new NullPointerException("instanceId can not be null or empty.");
        if (mqClients.containsKey(instanceId)) return mqClients.get(instanceId);
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer();
        mqClients.putIfAbsent(instanceId, defaultMQPushConsumer);
        return defaultMQPushConsumer;
    }


    protected static void destory(String instanceId) {
        if (Objects.isNull(instanceId) || instanceId.trim().isEmpty())
            throw new NullPointerException("instanceId can not be null or empty.");
        if (!mqClients.containsKey(instanceId))
            throw new RuntimeException("InstanceId:" + instanceId + " has not created");
        DefaultMQPushConsumer defaultMQPushConsumer = mqClients.get(instanceId);
        if (Objects.nonNull(defaultMQPushConsumer))
            defaultMQPushConsumer.shutdown();
    }

}
