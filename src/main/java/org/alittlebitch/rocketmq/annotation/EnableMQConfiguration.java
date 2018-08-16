package org.alittlebitch.rocketmq.annotation;

import org.alittlebitch.rocketmq.config.MQConfig;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.SchedulingConfiguration;

import java.lang.annotation.*;

/**
 * @author ShawnShoper
 * @date 2018/7/26 10:39
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(MQConfig.class)
@Documented
public @interface EnableMQConfiguration {
}
