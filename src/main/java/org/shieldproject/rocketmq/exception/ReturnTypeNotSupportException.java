package org.shieldproject.rocketmq.exception;

/**
 * @author ShawnShoper
 * @date 2018/9/17 15:00
 */
public class ReturnTypeNotSupportException extends RuntimeException {
    public ReturnTypeNotSupportException() {
        super();
    }

    public ReturnTypeNotSupportException(String message) {
        super(message);
    }
}
