package org.alittlebitch.rocketmq.context;

import org.apache.rocketmq.common.message.MessageQueue;

/**
 * @author ShawnShoper
 * @date 2018/8/14 15:39
 */
public class ConsumeContextConcurrently extends ConsumeContext {
    /**
     * Message consume retry strategy<br>
     * -1,no retry,put into DLQ directly<br>
     * 0,broker control retry frequency<br>
     * >0,client control retry frequency
     */
    private int delayLevelWhenNextConsume = 0;
    private int ackIndex = Integer.MAX_VALUE;

    public ConsumeContextConcurrently(MessageQueue messageQueue) {
        super(messageQueue);
    }

    public int getDelayLevelWhenNextConsume() {
        return delayLevelWhenNextConsume;
    }

    public void setDelayLevelWhenNextConsume(int delayLevelWhenNextConsume) {
        this.delayLevelWhenNextConsume = delayLevelWhenNextConsume;
    }

    public int getAckIndex() {
        return ackIndex;
    }

    public void setAckIndex(int ackIndex) {
        this.ackIndex = ackIndex;
    }
}
