package org.alittlebitch.rocketmq.factory;

import lombok.Data;
import org.alittlebitch.rocketmq.annotation.RocketMQListener;

import java.lang.reflect.Method;

/**
 * @author ShawnShoper
 * @date 2018/8/30 11:33
 */
@Data
public class RocketMQMappingBean {
    private Object bean;
    private Method method;
    private RocketMQListener rocketMQListener;
}
