package com.jrymos.spring.boot.init;

import com.jrymos.spring.ioc.utils.PrintUtils;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.ResolvableType;

public class LogbackInitializerApplicationListener implements GenericApplicationListener {

    @Override
    public boolean supportsEventType(ResolvableType eventType) {
        return ApplicationEnvironmentPreparedEvent.class.isAssignableFrom(eventType.getRawClass());
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        PrintUtils.errorLog();
    }

    @Override
    public int getOrder() {
        //最高优先级
        return Ordered.HIGHEST_PRECEDENCE;
    }
}