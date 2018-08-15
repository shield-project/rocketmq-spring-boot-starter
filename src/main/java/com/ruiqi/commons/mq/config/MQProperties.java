package com.ruiqi.commons.mq.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.rocketmq.client.ClientConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author ShawnShoper
 * @date 2018/7/25 16:05
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.mq.rocket")
public class MQProperties {
    @NestedConfigurationProperty
    private ClientConfig config = new ClientConfig();
    @NestedConfigurationProperty
    private ProducerProperties producer = new ProducerProperties();
    @NestedConfigurationProperty
    private ConsumerProperties consumer = new ConsumerProperties();
}
