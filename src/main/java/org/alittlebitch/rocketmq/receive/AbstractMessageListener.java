package org.alittlebitch.rocketmq.receive;

import lombok.extern.log4j.Log4j;
import org.alittlebitch.rocketmq.annotation.ConsumerConfig;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/7/30 17:31
 */
@Log4j
public abstract class AbstractMessageListener<T extends ConsumeContext> {
    @Autowired
    DefaultMQPushConsumer mqConsumer;
    @Autowired
    MessageRecevieHandler messageRecevieHandler;

    @PostConstruct
    public void init() throws MQClientException, NoSuchMethodException {
        ConsumerConfig consumerConfig = getConsumerConfig();
        String topic = consumerConfig.topic();
        ConsumeFromWhere consumeFromWhere = consumerConfig.consumeFromWhere();
        String tags = consumerConfig.tags();
        //获取实际处理listener
        Class<T> actualListener = getActualListener();

        if (actualListener == ConsumeContextConcurrently.class)
            mqConsumer.registerMessageListener(this::messageHandle4Concurrently);
        else if (actualListener == ConsumeContextOrderly.class)
            mqConsumer.registerMessageListener(this::messageHandle4Orderly);
        else
            throw new RuntimeException("Not support this class [" + actualListener.getName() + "] for listener");

        mqConsumer.subscribe(topic, tags);
        mqConsumer.setConsumeFromWhere(consumeFromWhere);
        mqConsumer.start();
    }

    private ConsumerConfig getConsumerConfig() throws NoSuchMethodException {
        Method consumeMessage = this.getClass().getDeclaredMethod("consumeMessage", List.class, ConsumeContext.class);
        return consumeMessage.getAnnotation(ConsumerConfig.class);
    }

    private ConsumeOrderlyStatus messageHandle4Orderly(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        T consumeContext = messageRecevieHandler.messageHandle(msgs, context, ConsumeContextOrderly.class);
        ConsumeStatus consumeStatus = consumeMessage(msgs, consumeContext);
        if (consumeStatus.getToken() == 0x02)
            return consumeStatus.getConsumeOrderlyStatus();
        else
            return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
    }

    private ConsumeConcurrentlyStatus messageHandle4Concurrently(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        T consumeContext = messageRecevieHandler.messageHandle(msgs, context, ConsumeContextConcurrently.class);
        ConsumeStatus consumeStatus = consumeMessage(msgs, consumeContext);
        if (consumeStatus.getToken() == 0x01)
            return consumeStatus.getConsumeConcurrentlyStatus();
        else
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
    }

    protected Class getActualListener() {
        final Class<? extends AbstractMessageListener> classz = this.getClass();
        Type t = classz.getGenericSuperclass();
        Class actualClass = null;
        if (t instanceof ParameterizedType) {
            Type[] args = ((ParameterizedType) t).getActualTypeArguments();
            if (args[0] instanceof Class) {
                actualClass = (Class<T>) args[0];
            }
        }
        return actualClass;
    }

    public abstract ConsumeStatus consumeMessage(List<MessageExt> messageExts, T consumeContext);
}
