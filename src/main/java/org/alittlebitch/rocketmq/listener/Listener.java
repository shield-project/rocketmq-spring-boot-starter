package org.alittlebitch.rocketmq.listener;

import java.lang.reflect.Method;

/**
 * @author ShawnShoper
 * @date 2018/9/10 18:08
 */
public abstract class Listener {
    protected Object bean;
    protected Method method;

    public Listener(Object bean, Method method) {
        this.bean = bean;
        this.method = method;
    }
}
