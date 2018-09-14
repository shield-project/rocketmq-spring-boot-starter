package org.shieldproject.rocketmq.annotation;

import org.shieldproject.rocketmq.config.ContextConfig;
import org.shieldproject.rocketmq.config.RocketMQConfig;
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
