package org.alittlebitch.rocketmq.context;

import org.apache.rocketmq.common.message.MessageQueue;

/**
 * @author ShawnShoper
 * @date 2018/8/14 15:41
 */
public class ConsumeContextOrderly extends ConsumeContext {

    private boolean autoCommit = true;

    private long suspendCurrentQueueTimeMillis = -1;

    public ConsumeContextOrderly(MessageQueue messageQueue) {
        super(messageQueue);
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public long getSuspendCurrentQueueTimeMillis() {
        return suspendCurrentQueueTimeMillis;
    }

    public void setSuspendCurrentQueueTimeMillis(long suspendCurrentQueueTimeMillis) {
        this.suspendCurrentQueueTimeMillis = suspendCurrentQueueTimeMillis;
    }
}
