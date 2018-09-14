package org.shieldproject.rocketmq.config;

import org.shieldproject.rocketmq.factory.MQClientInstance;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ShawnShoper
 * @date 2018/7/25 15:21
 */
@Configuration
@EnableConfigurationProperties(MQProperties.class)
public class RocketMQConfig {

    @Autowired
    MQProperties mqProperties;

    @Bean
    @ConditionalOnMissingBean(MQProducer.class)
    public MQProducer mqProducer() throws MQClientException {

        ProducerProperties producerProperties = mqProperties.getProducer();
        // default producer
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
    public MQClientInstance mqClientInstance() {
        return new MQClientInstance(mqProperties.getConfig(), mqProperties.getConsumer());
    }
}
