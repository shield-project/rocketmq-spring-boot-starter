package org.shieldproject.rocketmq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ShawnShoper
 * @date 2018/8/30 15:09
 */
@Configuration
public class ContextConfig {
    @Bean
    public RocketMQContextListener rocketMQContextListener() {
        return new RocketMQContextListener();
    }

    @Bean
    public RocketMQContextRefreshedListener rocketMQContextRefreshedListener() {
        return new RocketMQContextRefreshedListener();
    }
}
