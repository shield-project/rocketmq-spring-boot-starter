package org.alittlebitch.rocketmq.config;

import org.alittlebitch.rocketmq.annotation.RocketMQListener;
import org.alittlebitch.rocketmq.bean.RocketMQMappingBean;
import org.alittlebitch.rocketmq.context.RocketMQMappingStore;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author ShawnShoper
 * @date 2018/8/30 11:02
 */
public class RocketMQContextListener implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        Map<Method, RocketMQListener> methodRocketMQListenerMap = MethodIntrospector.selectMethods(targetClass, (MethodIntrospector.MetadataLookup<RocketMQListener>) method -> {
            RocketMQListener listenerMethod = findListenerAnnotations(method);
            return (listenerMethod != null ? listenerMethod : null);
        });
        methodRocketMQListenerMap.entrySet().stream().map(entry -> {
            RocketMQMappingBean rocketMQMappingBean = new RocketMQMappingBean();
            Method key = entry.getKey();
            rocketMQMappingBean.setBean(bean);
            RocketMQListener value = entry.getValue();
            rocketMQMappingBean.setMethod(key);
            rocketMQMappingBean.setRocketMQListener(value);
            return rocketMQMappingBean;
        }).forEach(RocketMQMappingStore.getRocketmqMappingBeans()::add);
        return bean;
    }

    /**
     * find from class header with annotations
     * @param method
     * @return
     */
//    private Collection<RocketMQListener> findListenerAnnotations(Class<?> clazz) {
//        Set<RocketMQListener> listeners = new HashSet<>();
//        RocketMQListener ann = AnnotationUtils.findAnnotation(clazz, RocketMQListener.class);
//        if (Objects.nonNull(ann))
//            listeners.add(ann);
//        return listeners;
//    }

    /**
     * find from method header with annotations
     *
     * @param method
     * @return
     */
    private RocketMQListener findListenerAnnotations(Method method) {
        return AnnotationUtils.findAnnotation(method, RocketMQListener.class);
    }
}
