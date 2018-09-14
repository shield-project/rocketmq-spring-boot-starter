package org.shieldproject.rocketmq.test;

import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.shieldproject.rocketmq.annotation.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author ShawnShoper
 * @date 2018/7/30 11:34
 */
@Component
public class MessageListener {

    @RocketMQListener(instance = "testfor",
            topic = "test", tags = "test",
            consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET)
    public void consumeMessage(String msgs) {
        System.out.println(msgs + System.currentTimeMillis());
    }

}
