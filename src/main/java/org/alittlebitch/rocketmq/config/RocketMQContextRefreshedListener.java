package org.alittlebitch.rocketmq.config;

import org.alittlebitch.rocketmq.bean.RocketMQMappingBean;
import org.alittlebitch.rocketmq.context.RocketMQMappingStore;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/8/30 15:04
 */
public class RocketMQContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<RocketMQMappingBean> rocketMQMappingBeans = RocketMQMappingStore.getRocketmqMappingBeans();
        //加载Rocket mq client
        rocketMQMappingBeans.forEach(e -> {

        });
    }
}
