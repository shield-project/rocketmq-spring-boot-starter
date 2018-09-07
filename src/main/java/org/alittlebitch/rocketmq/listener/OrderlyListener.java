package org.alittlebitch.rocketmq.listener;

import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/9/7 15:07
 */
public class OrderlyListener implements MessageListenerOrderly {
    private Object bean;
    private Method method;

    public OrderlyListener(Object bean, Method method) {
        this.bean = bean;
        this.method = method;
    }

    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        if (msgs.isEmpty()) return ConsumeOrderlyStatus.SUCCESS;
        MessageExt messageExt = msgs.get(0);
        byte[] body = messageExt.getBody();
        String message = new String(body, Charset.forName("UTF-8"));
        try {
            method.invoke(bean, message);
        } catch (Exception e) {
            e.printStackTrace();
            return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
        }
        return ConsumeOrderlyStatus.SUCCESS;
    }
}
