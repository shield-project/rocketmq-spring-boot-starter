package org.alittlebitch.rocketmq.config;

import org.alittlebitch.rocketmq.annotation.ConsumerConfig;
import org.alittlebitch.rocketmq.annotation.ConsumerConfigProperty;
import org.alittlebitch.rocketmq.enums.ConsumeMode;
import org.alittlebitch.rocketmq.receive.MQMessageListener;
import org.alittlebitch.rocketmq.receive.MessageRecevieHandler;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * @author ShawnShoper
 * @date 2018/7/25 15:21
 */
@Configuration
@EnableConfigurationProperties(MQProperties.class)
public class MQConfig {
    @Autowired
    MQProperties mqProperties;

    @Bean
    @ConditionalOnMissingBean(ConsumerConfigProperty.class)
    public ConsumerConfigProperty produceConfigProperty(@Autowired(required = false) MQMessageListener mqMessageListener) throws NoSuchMethodException {
        ConsumerConfigProperty produceConfigProperty = new ConsumerConfigProperty();
        if (Objects.nonNull(mqMessageListener)) {
            Method consume = mqMessageListener.getClass().getDeclaredMethod("consume", List.class, ConsumeConcurrentlyContext.class);
            ConsumerConfig annotation = consume.getAnnotation(ConsumerConfig.class);
            if (Objects.nonNull(annotation)) {
                String topic = annotation.topic();
                String tags = annotation.tags();
                ConsumeMode consumeMode = annotation.consumeMode();
                ConsumeFromWhere consumeFromWhere = annotation.consumeFromWhere();
                produceConfigProperty.updateInstance(topic, tags, consumeMode, consumeFromWhere);
            }
        }
        return produceConfigProperty;
    }

    @Bean
    @ConditionalOnMissingBean(MQProducer.class)
    public MQProducer mqProducer() throws MQClientException {
        ProducerProperties producerProperties = mqProperties.getProducer();
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer();
        defaultMQProducer.setNamesrvAddr(mqProperties.getConfig().getNamesrvAddr());
        defaultMQProducer.setCompressMsgBodyOverHowmuch(producerProperties.getCompressMsgBodyOverHowmuch());
        defaultMQProducer.setSendMessageWithVIPChannel(mqProperties.getConfig().isVipChannelEnabled());
        defaultMQProducer.setInstanceName(mqProperties.getConfig().getInstanceName());
        defaultMQProducer.setProducerGroup(producerProperties.getGroup());
        defaultMQProducer.setDefaultTopicQueueNums(producerProperties.getDefaultTopicQueueNums());
        defaultMQProducer.setMaxMessageSize(producerProperties.getMaxMessageSize());
        defaultMQProducer.setRetryAnotherBrokerWhenNotStoreOK(producerProperties.isRetryAnotherBrokerWhenNotStoreOK());
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(producerProperties.getRetryTimesWhenSendAsyncFailed());
        defaultMQProducer.start();
        return defaultMQProducer;
    }

    @Bean
    @ConditionalOnMissingBean(DefaultMQPushConsumer.class)
    public DefaultMQPushConsumer mqConsumer() {
        DefaultMQPushConsumer mqConsumer = new DefaultMQPushConsumer();
        mqConsumer.setNamesrvAddr(mqProperties.getConfig().getNamesrvAddr());
        mqConsumer.setConsumeFromWhere(mqProperties.getConsumer().getConsumeFromWhere());
        mqConsumer.setConsumerGroup(mqProperties.getConsumer().getGroup());

//        mqConsumer.subscribe("test", "test");
//        mqConsumer.subscribe(consumerConfigProperty.getTopic(), consumerConfigProperty.getTags());
//        mqConsumer.setNamesrvAddr(mqProperties.getConfig().getNamesrvAddr());
//        ConsumerProperties consumerProperties = mqProperties.getConsumer();
//        mqConsumer.setConsumerGroup(consumerProperties.getGroup());
////        mqConsumer.setAdjustThreadPoolNumsThreshold(consumerProperties.getAdjustThreadPoolNumsThreshold());
////        mqConsumer.setConsumeConcurrentlyMaxSpan(consumerProperties.getConsumeConcurrentlyMaxSpan());
//        mqConsumer.setConsumeFromWhere(consumerConfigProperty.getConsumeFromWhere());
//        mqConsumer.setConsumeMessageBatchMaxSize(consumerProperties.getConsumeMessageBatchMaxSize());
        return mqConsumer;
    }

    @Bean
    public MessageRecevieHandler messageRecevieHandler() {
        return new MessageRecevieHandler();
    }
}
