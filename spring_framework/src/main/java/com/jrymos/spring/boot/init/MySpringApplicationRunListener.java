package com.jrymos.spring.boot.init;

import com.jrymos.spring.ioc.utils.PrintUtils;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.time.Duration;

public class MySpringApplicationRunListener implements SpringApplicationRunListener {

    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        PrintUtils.errorLog();
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        PrintUtils.errorLog();
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        PrintUtils.errorLog();
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        PrintUtils.errorLog();
    }

    @Override
    public void started(ConfigurableApplicationContext context, Duration timeTaken) {
        SpringApplicationRunListener.super.started(context, timeTaken);
    }

    @Override
    public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
        PrintUtils.errorLog();
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        PrintUtils.errorLog();
    }
}
