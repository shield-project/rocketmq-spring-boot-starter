package org.shieldproject.rocketmq.context;

import org.shieldproject.rocketmq.factory.RocketMQMappingBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ShawnShoper
 * @date 2018/8/30 14:50
 */
public class RocketMQMappingStore {
    private final static List<RocketMQMappingBean> ROCKETMQ_MAPPING_BEANS = new ArrayList<>();

    public static List<RocketMQMappingBean> getRocketmqMappingBeans() {
        return ROCKETMQ_MAPPING_BEANS;
    }
}
