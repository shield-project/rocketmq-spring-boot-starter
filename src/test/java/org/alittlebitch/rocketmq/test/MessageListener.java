package org.alittlebitch.rocketmq.test;

import org.alittlebitch.rocketmq.annotation.RocketMQListener;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.stereotype.Component;

/**
 * @author ShawnShoper
 * @date 2018/7/30 11:34
 */
@Component
public class MessageListener {
    @RocketMQListener(instance = "testfor", topic = "test", tags = "test", consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET)
    public void consumeMessage(String msgs) {
        System.out.println(msgs + System.currentTimeMillis());
    }
}
