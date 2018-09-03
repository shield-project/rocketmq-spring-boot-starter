package org.alittlebitch.rocketmq.config;

import org.alittlebitch.rocketmq.annotation.RocketMQListener;
import org.alittlebitch.rocketmq.context.RocketMQMappingStore;
import org.alittlebitch.rocketmq.factory.RocketMQMappingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;
import java.util.List;

/**
 * initialize all of the mq listener client instance
 *
 * @author ShawnShoper
 * @date 2018/8/30 15:04
 */
public class RocketMQContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<RocketMQMappingBean> rocketMQMappingBeans = RocketMQMappingStore.getRocketmqMappingBeans();
        //加载Rocket mq client
        rocketMQMappingBeans.forEach(e -> {
            Object bean = e.getBean();
            Method method = e.getMethod();
            RocketMQListener rocketMQListener = e.getRocketMQListener();
        });
    }
}
