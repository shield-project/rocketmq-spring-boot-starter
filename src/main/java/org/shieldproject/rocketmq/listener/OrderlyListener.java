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
        super.verify();
        Class<?>[] paramTypes = super.extractParamType();
        Object[] objects = super.assemblyData(paramTypes, msgs, context);
        //if user defined the return type,so return the user specify value
        ConsumeOrderlyStatus consumeOrderlyStatus = null;
        try {
            Class<?> returnType = method.getReturnType();
            if (!returnType.getName().equals("void")) {
                Object invoke = method.invoke(bean, objects);
                if (invoke instanceof ConsumeOrderlyStatus)
                    consumeOrderlyStatus = (ConsumeOrderlyStatus) invoke;
                else
                    throw new RuntimeException("Not support return type " + invoke.getClass().getName());
            } else {
                method.invoke(bean, objects);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
        }
        return consumeOrderlyStatus != null ? consumeOrderlyStatus : ConsumeOrderlyStatus.SUCCESS;
    }
}


//    Class<?>[] parameterTypes = method.getParameterTypes();
//    //start of DI   提取参数类型,用于进行注入
//    Object[] objects = new Object[parameterTypes.length];
//        for (int i = 0; i < parameterTypes.length; i++) {
//        if (String.class == parameterTypes[i]) {
//        if (!msgs.isEmpty()) {
//        MessageExt messageExt = msgs.get(0);
//        byte[] body = messageExt.getBody();
//        objects[i] = new String(body, Charset.forName("UTF-8"));
//        continue;
//        }
//        }
//        if (ConsumeOrderlyContext.class == parameterTypes[i]) {
//        objects[i] = context;
//        continue;
//        }
//        if (List.class == parameterTypes[i]) {
//        Type[] genericParameterTypes = method.getGenericParameterTypes();
//        Type listType = genericParameterTypes[i];
//        ParameterizedType parameterizedType = (ParameterizedType) listType;
//        Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
//        if ("class java.lang.String".equals(actualTypeArgument.getTypeName())) {
//        List<String> messages = new ArrayList<>();
//        for (MessageExt msg : msgs)
//        messages.add(new String(msg.getBody(), Charset.forName("UTF-8")));
//        objects[i] = messages;
//        continue;
//        } else {
//        logger.error("Method parameter " + parameterTypes[i] + " generics not support..");
//        }
//        }
//        objects[i] = null;
//        }