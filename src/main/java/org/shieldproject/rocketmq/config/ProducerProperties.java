package org.shieldproject.rocketmq.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ShawnShoper
 * @date 2018/7/25 16:05
 */
@Getter
@Setter
public class ProducerProperties {

    /**
     * The group name of producer
     */
    private String group = "DEFAULT";

    /**
     * Number of queues to create per default topic.
     */
    private int defaultTopicQueueNums = 4;

    /**
     * Timeout for sending messages.
     */
    private int sendMsgTimeout = 3000;

    /**
     * Compress message body threshold, namely, message body larger than 4k will be compressed on default.
     */
    private int compressMsgBodyOverHowmuch = 1024 * 4;

    /**
     * Maximum number of retry to perform internally before claiming sending failure in synchronous mode.
     * </p>
     * <p>
     * This may potentially cause message duplication which is up to application developers to resolve.
     */
    private int retryTimesWhenSendFailed = 2;

    /**
     * Maximum number of retry to perform internally before claiming sending failure in asynchronous mode.
     * </p>
     * <p>
     * This may potentially cause message duplication which is up to application developers to resolve.
     */
    private int retryTimesWhenSendAsyncFailed = 2;

    /**
     * Indicate whether to retry another broker on sending failure internally.
     */
    private boolean retryAnotherBrokerWhenNotStoreOK = false;

    /**
     * Maximum allowed message size in bytes.
     * max = 4 * 1024 * 1024
     */
    private int maxMessageSize = 1024 * 1024 * 4;
}
