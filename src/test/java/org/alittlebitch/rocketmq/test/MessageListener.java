package org.alittlebitch.rocketmq.test;

import org.alittlebitch.rocketmq.annotation.RocketMQListener;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/7/30 11:34
 */
@Component
public class MessageListener {

    @RocketMQListener(instance = "testfor1",
            topic = "test", tags = "test",
            consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET)
    public void consumeMessage(String msgs) {
        System.out.println(msgs + System.currentTimeMillis());
    }

    @RocketMQListener(instance = "testfor2",
            topic = "test", tags = "test",
            consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET)
    public void consumeMessage(List<String> msgs) {
        System.out.println(msgs.get(0) + System.currentTimeMillis());
    }

    @RocketMQListener(instance = "testfor3",
            topic = "test", tags = "test",
            consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET)
    public ConsumeOrderlyStatus consumeMessage1(List<String> msgs) {
        System.out.println(msgs.get(0) + System.currentTimeMillis());
        return ConsumeOrderlyStatus.SUCCESS;
    }
}
