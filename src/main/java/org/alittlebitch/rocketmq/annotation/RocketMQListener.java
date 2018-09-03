package org.alittlebitch.rocketmq.annotation;

import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

import java.lang.annotation.*;

/**
 * 用于在实现了org.alittlebitch.rocketmq.receive.ConcurrentlyMessageListener.consume方法头上添加
 *
 * @author ShawnShoper
 * @date 2018/7/26 11:52
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RocketMQListener {
    /**
     * client instance name.it will be rewrite by config file.
     *
     * @return
     */
    String instance();

    /**
     * topic name of consumer target
     *
     * @return
     */
    String topic() default "DEFAULT";

    /**
     * tag name of target topic
     *
     * @return
     */
    String tags();

    /**
     * group name of target client consumer client
     *
     * @return
     */
    String group();

    /**
     * consumer position offset
     *
     * @return
     */
    ConsumeFromWhere consumeFromWhere() default ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET;

    ConsumeContext consumeContext() default ConsumeContext.Orderly;


//    //    boolean autoCommit() default true;
//    ConsumeMode consumeMode() default ConsumeMode.CONCURRENTLY;
}
