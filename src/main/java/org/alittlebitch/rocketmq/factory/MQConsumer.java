package org.alittlebitch.rocketmq.factory;

import org.alittlebitch.rocketmq.annotation.RocketMQListener;
import org.alittlebitch.rocketmq.config.ConsumerProperties;
import org.apache.rocketmq.client.ClientConfig;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.util.StringUtils;

/**
 * @author ShawnShoper
 * @date 2018/9/5 14:41
 */
public class MQConsumer {
    DefaultMQPushConsumer mqPushConsumer;

    public MQConsumer() {
        mqPushConsumer = new DefaultMQPushConsumer();
    }

    public MQConsumer config(ClientConfig clientConfig, ConsumerProperties consumerProperties, RocketMQListener rocketMQListener) {
        mqPushConsumer.setInstanceName(StringUtils.isEmpty(rocketMQListener.instance()) ? clientConfig.getInstanceName() : rocketMQListener.instance());
        mqPushConsumer.setConsumerGroup(StringUtils.isEmpty(rocketMQListener.group()) ? consumerProperties.getGroup() : rocketMQListener.group());
        mqPushConsumer.setConsumeFromWhere(consumerProperties.getConsumeFromWhere() != null ? consumerProperties.getConsumeFromWhere() : ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        mqPushConsumer.setConsumeMessageBatchMaxSize(consumerProperties.getConsumeMessageBatchMaxSize());
        mqPushConsumer.setConsumeConcurrentlyMaxSpan(consumerProperties.getConsumeConcurrentlyMaxSpan());
        mqPushConsumer.setAdjustThreadPoolNumsThreshold(consumerProperties.getAdjustThreadPoolNumsThreshold());
        return this;
    }

    public void shutdown() {
        if (mqPushConsumer == null)
            throw new RuntimeException("MQConsumer has not initialized");
        mqPushConsumer.shutdown();
    }
}
