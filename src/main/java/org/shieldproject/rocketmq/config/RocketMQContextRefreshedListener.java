package org.shieldproject.rocketmq.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.rocketmq.client.exception.MQClientException;
import org.shieldproject.rocketmq.annotation.RocketMQListener;
import org.shieldproject.rocketmq.context.RocketMQMappingStore;
import org.shieldproject.rocketmq.factory.MQClientInstance;
import org.shieldproject.rocketmq.factory.MQConsumer;
import org.shieldproject.rocketmq.factory.RocketMQMappingBean;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
public class RocketMQContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    private static final Log logger = LogFactory.getLog(RocketMQContextRefreshedListener.class);
    ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<RocketMQMappingBean> rocketMQMappingBeans = RocketMQMappingStore.getRocketmqMappingBeans();
        //registry Rocket mq client
        rocketMQMappingBeans.forEach(e -> {
            Object bean = e.getBean();
            Method method = e.getMethod();
            RocketMQListener rocketMQListener = e.getRocketMQListener();
            MQClientInstance mqClientInstance = applicationContext.getBean(MQClientInstance.class);
            try {
                MQConsumer mqConsumer = mqClientInstance.createInstance(bean, method, rocketMQListener);
                mqConsumer.start();
            } catch (MQClientException e1) {
                logger.error("Failed Start mq client", e1);
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
