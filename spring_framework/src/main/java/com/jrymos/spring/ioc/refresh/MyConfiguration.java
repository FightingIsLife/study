package com.jrymos.spring.ioc.refresh;

import com.jrymos.spring.ioc.utils.PrintUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@MyInterfaceScan(basePackages = "com.jrymos.spring.ioc.refresh.ifc")
//@Import(MyInterfaceImportBeanDefinitionRegistrar.class)
public class MyConfiguration {

    public MyConfiguration() {
        PrintUtils.errorLog();
    }

    @Bean
    public MyBean  mybean() {
        return new MyBean();
    }

    public static class MyBean {

    }
}
