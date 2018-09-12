package org.alittlebitch.rocketmq.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.common.message.MessageExt;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/9/10 18:08
 */
public abstract class Listener {
    protected Object bean;
    protected Method method;
    private static final Log logger = LogFactory.getLog(OrderlyListener.class);
    public Listener(Object bean, Method method) {
        this.bean = bean;
        this.method = method;
    }

    protected void verify() {
        if (method.getParameterCount() <= 0)
            throw new RuntimeException("RocketMQListener method [" + method.getName() + "] has no parameters");
    }

    protected Object[] assemblyData(Class<?>[] paramTypes, List<MessageExt> msgs, Object context) {//ConsumeOrderlyContext
        Object[] objects = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            if (String.class == paramTypes[i]) {
                if (!msgs.isEmpty()) {
                    MessageExt messageExt = msgs.get(0);
                    byte[] body = messageExt.getBody();
                    objects[i] = new String(body, Charset.forName("UTF-8"));
                    continue;
                }
            }
            if (ConsumeOrderlyContext.class == paramTypes[i]) {
                objects[i] = context;
                continue;
            }
            if (List.class == paramTypes[i]) {
                Type[] genericParameterTypes = method.getGenericParameterTypes();
                Type listType = genericParameterTypes[i];
                ParameterizedType parameterizedType = (ParameterizedType) listType;
                Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
                if ("class java.lang.String".equals(actualTypeArgument.getTypeName())) {
                    List<String> messages = new ArrayList<>();
                    for (MessageExt msg : msgs)
                        messages.add(new String(msg.getBody(), Charset.forName("UTF-8")));
                    objects[i] = messages;
                    continue;
                } else {
                    logger.error("Method parameter " + paramTypes[i] + " generics not support..");
                }
            }
            objects[i] = null;
        }
        return objects;
    }

    protected Class<?>[] extractParamType() {
        return this.method.getParameterTypes();
    }
}
