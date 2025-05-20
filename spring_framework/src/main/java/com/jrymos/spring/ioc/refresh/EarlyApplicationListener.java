package com.jrymos.spring.ioc.refresh;

import com.jrymos.spring.ioc.utils.PrintUtils;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;


public class EarlyApplicationListener implements ApplicationListener {
    public EarlyApplicationListener() {
        PrintUtils.errorLog();
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        PrintUtils.errorLog(event.toString());
    }
}
