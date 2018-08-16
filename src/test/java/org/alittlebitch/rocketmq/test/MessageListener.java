package org.alittlebitch.rocketmq.test;

import org.alittlebitch.rocketmq.annotation.ConsumerConfig;
import org.alittlebitch.rocketmq.receive.AbstractMessageListener;
import org.alittlebitch.rocketmq.receive.ConsumeContextConcurrently;
import org.alittlebitch.rocketmq.receive.ConsumeStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/7/30 11:34
 */
@Component
public class MessageListener extends AbstractMessageListener<ConsumeContextConcurrently> {
    @Override
    @ConsumerConfig(topic = "test", tags = "test", consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET)
    public ConsumeStatus consumeMessage(List<MessageExt> msgs, ConsumeContextConcurrently context) {
        try {
            String message = new String(msgs.get(0).getBody(), "UTF-8");
            System.out.println(new String(message) + System.currentTimeMillis());
            return ConsumeStatus.custom().setConsumeConcurrentlyStatus(ConsumeConcurrentlyStatus.CONSUME_SUCCESS);
        } catch (UnsupportedEncodingException e) {
            System.out.println(".........");
            e.printStackTrace();
            return ConsumeStatus.custom().setConsumeConcurrentlyStatus(ConsumeConcurrentlyStatus.RECONSUME_LATER);
        }
    }
}
