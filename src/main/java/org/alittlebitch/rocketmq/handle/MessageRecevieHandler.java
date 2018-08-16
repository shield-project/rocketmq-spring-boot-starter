package org.alittlebitch.rocketmq.handle;

import org.alittlebitch.rocketmq.context.ConsumeContext;
import org.alittlebitch.rocketmq.context.ConsumeContextConcurrently;
import org.alittlebitch.rocketmq.context.ConsumeContextOrderly;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/8/14 17:05
 */
public class MessageRecevieHandler {
    public <T> T messageHandle(List<MessageExt> messageExts, ConsumeOrderlyContext consumeOrderlyContext, Class<? extends ConsumeContext> t) {
        Assert.notNull(consumeOrderlyContext, "Consume context can not be null.");
        ConsumeContextOrderly consumeContextOrderly = new ConsumeContextOrderly(consumeOrderlyContext.getMessageQueue());
        long suspendCurrentQueueTimeMillis = consumeOrderlyContext.getSuspendCurrentQueueTimeMillis();
        boolean autoCommit = consumeOrderlyContext.isAutoCommit();
        consumeContextOrderly.setSuspendCurrentQueueTimeMillis(suspendCurrentQueueTimeMillis);
        consumeContextOrderly.setAutoCommit(autoCommit);
        return (T) consumeContextOrderly;
    }

    public <T> T messageHandle(List<MessageExt> messageExts, ConsumeConcurrentlyContext consumeConcurrentlyContext, Class<? extends ConsumeContext> t) {
        Assert.notNull(consumeConcurrentlyContext, "Consume context can not be null.");
        ConsumeContextConcurrently consumeContext = new ConsumeContextConcurrently(consumeConcurrentlyContext.getMessageQueue());
        int delayLevelWhenNextConsume = consumeConcurrentlyContext.getDelayLevelWhenNextConsume();
        int ackIndex = consumeConcurrentlyContext.getAckIndex();
        consumeContext.setDelayLevelWhenNextConsume(delayLevelWhenNextConsume);
        consumeContext.setAckIndex(ackIndex);

        return (T) consumeContext;
    }
}
