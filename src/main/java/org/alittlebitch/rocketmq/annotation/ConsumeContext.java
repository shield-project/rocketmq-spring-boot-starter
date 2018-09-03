package org.alittlebitch.rocketmq.annotation;

/**
 * @author ShawnShoper
 * @date 2018/9/3 15:56
 */
public enum ConsumeContext {
    /**
     * support concurrent consumer
     */
    Concurrently,
    /**
     * support orderly consumer
     */
    Orderly;
}
