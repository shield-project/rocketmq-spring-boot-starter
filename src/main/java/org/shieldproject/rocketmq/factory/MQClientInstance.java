package org.shieldproject.rocketmq.factory;

import org.shieldproject.rocketmq.annotation.ConsumeContext;
import org.shieldproject.rocketmq.annotation.RocketMQListener;
import org.shieldproject.rocketmq.config.ConsumerProperties;
import org.shieldproject.rocketmq.listener.ConcurrentlyListener;
import org.shieldproject.rocketmq.listener.OrderlyListener;
import org.apache.rocketmq.client.ClientConfig;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author ShawnShoper
 * @date 2018/9/3 15:38
 */
public class MQClientInstance {

    ClientConfig clientConfig;
    ConsumerProperties consumerProperties;

    public MQClientInstance(ClientConfig clientConfig, ConsumerProperties consumerProperties) {
        this.consumerProperties = consumerProperties;
        this.clientConfig = clientConfig;
    }

    public MQConsumer createInstance(Object bean, Method method, RocketMQListener rocketMQListener) throws MQClientException {
        String instance = StringUtils.isEmpty(rocketMQListener.instance()) ? clientConfig.getInstanceName() : rocketMQListener.instance();
        if (StringUtils.isEmpty(instance))
            throw new NullPointerException("Instance name can not be null or empty");
        //extract instance id
        MQConsumer defaultMQPushConsumer = MQClientFactory.registry(instance, clientConfig, consumerProperties, rocketMQListener);
        if (ConsumeContext.Orderly == rocketMQListener.consumeContext())
            defaultMQPushConsumer.listener(new OrderlyListener(bean, method));
        else if (ConsumeContext.Concurrently == rocketMQListener.consumeContext())
            defaultMQPushConsumer.listener(new ConcurrentlyListener(bean, method));
        else
            throw new RuntimeException("ConsumeContext not support now.. [" + rocketMQListener.consumeContext().name() + "]");
//        defaultMQPushConsumer.setInstanceName(instance);
//        if (!StringUtils.isEmpty(rocketMQListener.group()))
//            defaultMQPushConsumer.setConsumerGroup(rocketMQListener.group());
//        defaultMQPushConsumer.sub();
        return defaultMQPushConsumer;
    }
}
