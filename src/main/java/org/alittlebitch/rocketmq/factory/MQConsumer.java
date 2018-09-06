package org.alittlebitch.rocketmq.factory;

import org.alittlebitch.rocketmq.annotation.RocketMQListener;
import org.alittlebitch.rocketmq.config.ConsumerProperties;
import org.apache.rocketmq.client.ClientConfig;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author ShawnShoper
 * @date 2018/9/5 14:41
 */
public class MQConsumer {
    private final String DEFAULT_NAME_SRV_ADDR = "127.0.0.1:9876";
    private DefaultMQPushConsumer mqPushConsumer;

    private MQConsumer() {

    }

    public static MQConsumer custom() {
        MQConsumer mqConsumer = new MQConsumer();
        mqConsumer.mqPushConsumer = new DefaultMQPushConsumer();
        return mqConsumer;
    }

    public MQConsumer config(ClientConfig clientConfig, ConsumerProperties consumerProperties, RocketMQListener rocketMQListener) {
        mqPushConsumer.setNamesrvAddr(StringUtils.isEmpty(clientConfig.getNamesrvAddr()) ? DEFAULT_NAME_SRV_ADDR : clientConfig.getNamesrvAddr());
        mqPushConsumer.setInstanceName(StringUtils.isEmpty(rocketMQListener.instance()) ? clientConfig.getInstanceName() : rocketMQListener.instance());
        mqPushConsumer.setConsumerGroup(StringUtils.isEmpty(rocketMQListener.group()) ? consumerProperties.getGroup() : rocketMQListener.group());
        mqPushConsumer.setConsumeFromWhere(consumerProperties.getConsumeFromWhere() != null ? consumerProperties.getConsumeFromWhere() : ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        mqPushConsumer.setConsumeMessageBatchMaxSize(consumerProperties.getConsumeMessageBatchMaxSize());
        mqPushConsumer.setConsumeConcurrentlyMaxSpan(consumerProperties.getConsumeConcurrentlyMaxSpan());
        mqPushConsumer.setAdjustThreadPoolNumsThreshold(consumerProperties.getAdjustThreadPoolNumsThreshold());
        if (consumerProperties.getConsumeThreadMax() >= 0)
            mqPushConsumer.setConsumeThreadMax(consumerProperties.getConsumeThreadMax());
        if (consumerProperties.getConsumeThreadMin() >= 0)
            mqPushConsumer.setConsumeThreadMin(consumerProperties.getConsumeThreadMin());
        if (consumerProperties.getConsumeTimeout() >= 0)
            mqPushConsumer.setConsumeTimeout(consumerProperties.getConsumeTimeout());
        if (!StringUtils.isEmpty(consumerProperties.getConsumeTimestamp()))
            mqPushConsumer.setConsumeTimestamp(consumerProperties.getConsumeTimestamp());
        if (consumerProperties.getMaxReconsumeTimes() >= 0)
            mqPushConsumer.setMaxReconsumeTimes(consumerProperties.getMaxReconsumeTimes());
        if (Objects.nonNull(consumerProperties.getMessageModel()))
            mqPushConsumer.setMessageModel(consumerProperties.getMessageModel());
        if (Objects.nonNull(consumerProperties.getOffsetStore()))
            mqPushConsumer.setOffsetStore(consumerProperties.getOffsetStore());
        //TODO, the other config property...coming soon
        return this;
    }


    public void shutdown() {
        if (mqPushConsumer == null)
            throw new RuntimeException("MQConsumer has not initialized");
        mqPushConsumer.shutdown();
    }

}
