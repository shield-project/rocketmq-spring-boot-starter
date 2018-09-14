package org.shieldproject.rocketmq.factory;

import org.shieldproject.rocketmq.annotation.RocketMQListener;
import org.shieldproject.rocketmq.config.ConsumerProperties;
import org.shieldproject.rocketmq.exception.InstanceAlreadyRegistryException;
import org.apache.rocketmq.client.ClientConfig;
import org.apache.rocketmq.client.exception.MQClientException;

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


    public static MQConsumer registry(String instanceId, ClientConfig clientConfig, ConsumerProperties consumerProperties, RocketMQListener rocketMQListener) throws MQClientException {

        if (Objects.isNull(instanceId) || instanceId.trim().isEmpty())
            throw new NullPointerException("instanceId can not be null or empty.");

        if (mqClients.containsKey(instanceId))
            throw new InstanceAlreadyRegistryException("Instance " + instanceId + " is already reigstry,please check out and change the other one");

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

    public static void destoryAll() {
        mqClients.keySet().forEach(MQClientFactory::destory);
    }
}
