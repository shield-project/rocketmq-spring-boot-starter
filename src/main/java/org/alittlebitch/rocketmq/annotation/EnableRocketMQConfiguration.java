package org.alittlebitch.rocketmq.annotation;

import org.alittlebitch.rocketmq.config.ContextConfig;
import org.alittlebitch.rocketmq.config.RocketMQConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author ShawnShoper
 * @date 2018/7/26 10:39
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RocketMQConfig.class, ContextConfig.class})
@Documented
public @interface EnableRocketMQConfiguration {
}
