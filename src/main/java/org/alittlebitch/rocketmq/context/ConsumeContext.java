package org.alittlebitch.rocketmq.context;

import org.apache.rocketmq.common.message.MessageQueue;

/**
 * @author ShawnShoper
 * @date 2018/8/14 15:42
 */
public abstract class ConsumeContext<T> {

    private final MessageQueue messageQueue;

    public ConsumeContext(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    public MessageQueue getMessageQueue() {
        return messageQueue;
    }

}
