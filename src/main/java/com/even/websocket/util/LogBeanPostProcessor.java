package com.even.websocket.util;

import com.even.websocket.annation.Log;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * Created by yidadi on 17-5-11.
 */
@Component
public class LogBeanPostProcessor implements BeanPostProcessor{
    @Override
    public Object postProcessBeforeInitialization(Object bean, String s) throws BeansException {
        Field[] fiels = bean.getClass().getDeclaredFields();
        for (Field field : fiels) {
            Log log = field.getAnnotation(Log.class);
            if(log != null){
                try {
                    field.setAccessible(true);
                    field.set(bean, LoggerFactory.getLogger(bean.getClass()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }finally {
                    field.setAccessible(false);
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String s) throws BeansException {
        return bean;
    }
}
