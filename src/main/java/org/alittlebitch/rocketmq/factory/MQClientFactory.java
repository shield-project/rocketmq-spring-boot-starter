package org.alittlebitch.rocketmq.factory;

import org.alittlebitch.rocketmq.annotation.RocketMQListener;
import org.alittlebitch.rocketmq.config.ConsumerProperties;
import org.apache.rocketmq.client.ClientConfig;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ShawnShoper
 * @date 2018/8/30 16:58
 */
public class MQClientFactory {

    //instance id for key,DefaultMQPushConsumer for value
    protected static Map<String, MQConsumer> mqClients = new ConcurrentHashMap<>();


    protected static MQConsumer get(String instanceId, ClientConfig clientConfig, ConsumerProperties consumerProperties, RocketMQListener rocketMQListener) {
        if (Objects.isNull(instanceId) || instanceId.trim().isEmpty())
            throw new NullPointerException("instanceId can not be null or empty.");
        if (mqClients.containsKey(instanceId)) return mqClients.get(instanceId);
        MQConsumer defaultMQPushConsumer = MQConsumer.custom().config(clientConfig, consumerProperties, rocketMQListener);
        mqClients.putIfAbsent(instanceId, defaultMQPushConsumer);
        return defaultMQPushConsumer;
    }


    protected static void destory(String instanceId) {
        if (Objects.isNull(instanceId) || instanceId.trim().isEmpty())
            throw new NullPointerException("instanceId can not be null or empty.");
        if (!mqClients.containsKey(instanceId))
            throw new RuntimeException("InstanceId:" + instanceId + " has not created");
        MQConsumer defaultMQPushConsumer = mqClients.get(instanceId);
        if (Objects.nonNull(defaultMQPushConsumer))
            defaultMQPushConsumer.shutdown();
    }

}
