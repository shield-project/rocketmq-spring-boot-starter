package org.alittlebitch.rocketmq.receive;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * @author ShawnShoper
 * @date 2018/8/15 11:07
 */
public class ConsumeStatus {
    private int token;
    private ConsumeConcurrentlyStatus consumeConcurrentlyStatus;
    private ConsumeOrderlyStatus consumeOrderlyStatus;

    public static ConsumeStatus custom() {
        return new ConsumeStatus();
    }

    public int getToken() {
        return token;
    }

    public ConsumeConcurrentlyStatus getConsumeConcurrentlyStatus() {
        return consumeConcurrentlyStatus;
    }

    public ConsumeStatus setConsumeConcurrentlyStatus(ConsumeConcurrentlyStatus consumeConcurrentlyStatus) {
        assertNull(consumeConcurrentlyStatus);
        this.token = 0x01;
        this.consumeConcurrentlyStatus = consumeConcurrentlyStatus;
        return this;
    }

    public ConsumeOrderlyStatus getConsumeOrderlyStatus() {
        return consumeOrderlyStatus;
    }

    public ConsumeStatus setConsumeOrderlyStatus(ConsumeOrderlyStatus consumeOrderlyStatus) {
        assertNull(consumeOrderlyStatus);
        this.token = 0x02;
        this.consumeOrderlyStatus = consumeOrderlyStatus;
        return this;
    }
    private void assertNull(Object consumeStatus){
        if (Objects.isNull(consumeStatus))
            throw new RuntimeException("Return status can not be null");
    }
}
