package com.ruiqi.commons.mq.annotation;

import com.ruiqi.commons.mq.enums.ConsumeMode;
import lombok.Getter;
import lombok.Setter;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

/**
 * @author ShawnShoper
 * @date 2018/7/27 10:45
 */
@Getter
@Setter
public class ConsumerConfigProperty {
    String topic = "DEFAULT";
    String tags = "DEFAULT";
    ConsumeMode consumeMode = ConsumeMode.CONCURRENTLY;
    ConsumeFromWhere consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET;

    public static ConsumerConfigProperty newInstance(String topic, String tags, ConsumeMode consumeMode) {
        ConsumerConfigProperty produceConfigProperty = new ConsumerConfigProperty();
        produceConfigProperty.setConsumeMode(consumeMode);
        produceConfigProperty.setTags(tags);
        produceConfigProperty.setTopic(topic);
        return produceConfigProperty;
    }

    public void updateInstance(String topic, String tags, ConsumeMode consumeMode, ConsumeFromWhere consumeFromWhere) {
        this.setTopic(topic);
        this.setTags(tags);
        this.setConsumeMode(consumeMode);
        this.setConsumeFromWhere(consumeFromWhere);
    }
}
