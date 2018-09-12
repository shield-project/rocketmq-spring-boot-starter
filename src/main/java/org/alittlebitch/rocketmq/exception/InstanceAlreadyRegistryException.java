package org.alittlebitch.rocketmq.exception;

/**
 * @author ShawnShoper
 * @date 2018/9/12 10:44
 */
public class InstanceAlreadyRegistryException extends RuntimeException {

    public InstanceAlreadyRegistryException(String msg) {
        super(msg);
    }
}
