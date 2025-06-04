package com.jrymos.spring.boot.init;

import com.jrymos.spring.ioc.utils.PrintUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

public class FamoEnvironmentPostProcessor implements ApplicationContextInitializer<ConfigurableApplicationContext>, EnvironmentPostProcessor, Ordered {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        PrintUtils.errorLog();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        PrintUtils.errorLog();
    }


    /**
     * 控制ApplicationContextInitializer的顺序。 优先级应高于 ApolloApplicationContextInitializer
     *
     * @return
     */
    @Override
    public int getOrder() {
        return -1;
    }
}
