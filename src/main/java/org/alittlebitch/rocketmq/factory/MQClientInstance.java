package org.alittlebitch.rocketmq.factory;

import org.alittlebitch.rocketmq.annotation.RocketMQListener;
import org.alittlebitch.rocketmq.config.ConsumerProperties;
import org.alittlebitch.rocketmq.listener.OrderlyListener;
import org.apache.rocketmq.client.ClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author ShawnShoper
 * @date 2018/9/3 15:38
 */
@Component
public class MQClientInstance {
    @Autowired
    ClientConfig clientConfig;
    @Autowired
    ConsumerProperties consumerProperties;

    public MQConsumer createInstance(Object bean, Method method, RocketMQListener rocketMQListener) {
        String instance = rocketMQListener.instance();
        if (StringUtils.isEmpty(instance))
            throw new NullPointerException("Instance name can not be null or empty");
        MQConsumer defaultMQPushConsumer = MQClientFactory.get(instance, clientConfig, consumerProperties, rocketMQListener);
        defaultMQPushConsumer.listener(new OrderlyListener(bean, method));

//        defaultMQPushConsumer.setInstanceName(instance);
//        if (!StringUtils.isEmpty(rocketMQListener.group()))
//            defaultMQPushConsumer.setConsumerGroup(rocketMQListener.group());
//        defaultMQPushConsumer.sub();
        return defaultMQPushConsumer;
    }
}
