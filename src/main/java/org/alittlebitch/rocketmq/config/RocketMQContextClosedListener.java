package org.alittlebitch.rocketmq.config;

import org.alittlebitch.rocketmq.factory.MQClientFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * close all of the mq listener client instance when the context shutdown.
 *
 * @author ShawnShoper
 * @date 2018/8/30 15:04
 */
public class RocketMQContextClosedListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        MQClientFactory.destoryAll();
    }
}
