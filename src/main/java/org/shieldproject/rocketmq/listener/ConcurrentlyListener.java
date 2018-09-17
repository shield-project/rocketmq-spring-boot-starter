package org.shieldproject.rocketmq.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/9/10 18:06
 */
public class ConcurrentlyListener extends Listener implements MessageListenerConcurrently {
    private static final Log logger = LogFactory.getLog(ConcurrentlyListener.class);

    public ConcurrentlyListener(Object bean, Method method) {
        super(bean, method);
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        if (msgs.isEmpty()) return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        Object[] objects = super.assemblyData(msgs, context);
        //if user defined the  return type,so return the user specify value
        ConsumeConcurrentlyStatus consumeConcurrentlyStatus = null;
        try {
            Class<?> returnType = method.getReturnType();
            if (!returnType.isAssignableFrom(Void.TYPE)) {
                Object invoke = method.invoke(bean, objects);
                consumeConcurrentlyStatus = (ConsumeConcurrentlyStatus) invoke;
            }
        } catch (Exception e) {
            logger.error("Receive message failed", e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return consumeConcurrentlyStatus != null ? consumeConcurrentlyStatus : ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
