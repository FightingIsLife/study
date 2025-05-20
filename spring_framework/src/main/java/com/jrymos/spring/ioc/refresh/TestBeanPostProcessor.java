package com.jrymos.spring.ioc.refresh;

import com.jrymos.spring.ioc.utils.PrintUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class TestBeanPostProcessor implements BeanPostProcessor {

    public TestBeanPostProcessor() {
        PrintUtils.errorLog();
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        PrintUtils.errorLog(beanName, bean.toString());
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        PrintUtils.errorLog(beanName, bean.toString());
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
