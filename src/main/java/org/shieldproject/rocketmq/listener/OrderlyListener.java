package org.shieldproject.rocketmq.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/9/7 15:07
 */
public class OrderlyListener extends Listener implements MessageListenerOrderly {
    private static final Log logger = LogFactory.getLog(OrderlyListener.class);

    public OrderlyListener(Object bean, Method method) {
        super(bean, method);
    }

    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        if (msgs.isEmpty()) return ConsumeOrderlyStatus.SUCCESS;

        Object[] objects = super.assemblyData(msgs, context);
        //if user defined the return type,so return the user specify value
        ConsumeOrderlyStatus consumeOrderlyStatus = null;
        try {
            Class<?> returnType = method.getReturnType();
            if (!returnType.isAssignableFrom(Void.TYPE)) {
                Object invoke = method.invoke(bean, objects);
                if (invoke instanceof ConsumeOrderlyStatus)
                    consumeOrderlyStatus = (ConsumeOrderlyStatus) invoke;
                else
                    throw new RuntimeException("Not support return type " + invoke.getClass().getName());
            } else {
                method.invoke(bean, objects);
            }
        } catch (Exception e) {
            logger.error("Receive message failed", e);
            return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
        }
        return consumeOrderlyStatus != null ? consumeOrderlyStatus : ConsumeOrderlyStatus.SUCCESS;
    }
}