package com.jrymos.spring.ioc.refresh;

import com.jrymos.spring.ioc.utils.PrintUtils;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationListener implements ApplicationListener {

    public MyApplicationListener() {
        PrintUtils.errorLog();
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        PrintUtils.errorLog(event.toString());
    }
}
