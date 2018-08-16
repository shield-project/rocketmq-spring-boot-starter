package org.alittlebitch.rocketmq.receive;

import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/7/26 11:41
 */
public abstract class MQMessageListener {
    public Object consume(List<MessageExt> msgs, Object context) {
        return null;
    }
}
