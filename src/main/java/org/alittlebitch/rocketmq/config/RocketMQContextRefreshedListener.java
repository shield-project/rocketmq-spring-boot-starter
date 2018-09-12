package org.alittlebitch.rocketmq.config;

import org.alittlebitch.rocketmq.annotation.RocketMQListener;
import org.alittlebitch.rocketmq.context.RocketMQMappingStore;
import org.alittlebitch.rocketmq.factory.MQClientInstance;
import org.alittlebitch.rocketmq.factory.MQConsumer;
import org.alittlebitch.rocketmq.factory.RocketMQMappingBean;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

/**
 * initialize all of the mq listener client instance
 *
 * @author ShawnShoper
 * @date 2018/8/30 15:04
 */
@Component
public class RocketMQContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    MQClientInstance mqClientInstance;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        List<RocketMQMappingBean> rocketMQMappingBeans = RocketMQMappingStore.getRocketmqMappingBeans();
        //registry Rocket mq client
        rocketMQMappingBeans.forEach(e -> {
            Object bean = e.getBean();
            Method method = e.getMethod();
            RocketMQListener rocketMQListener = e.getRocketMQListener();
            try {
                MQConsumer mqConsumer = mqClientInstance.createInstance(bean, method, rocketMQListener);
                mqConsumer.start();
            } catch (MQClientException e1) {
                e1.printStackTrace();
            }
        });
    }
}
