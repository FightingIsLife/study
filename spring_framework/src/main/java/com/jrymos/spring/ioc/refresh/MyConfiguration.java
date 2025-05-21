package com.jrymos.spring.ioc.refresh;

import com.jrymos.spring.ioc.refresh.ifc.TestTargetBean;
import com.jrymos.spring.ioc.utils.PrintUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;

@Configuration
@MyInterfaceScan(basePackages = "com.jrymos.spring.ioc.refresh.ifc")
public class MyConfiguration {

    public MyConfiguration() {
        PrintUtils.errorLog();
    }


    public String hello() {
        return null;
    }

    @Bean
    public MyBean mybean() {
        PrintUtils.errorLog();
        return new MyBean();
    }


    @Import({OtherImportBean.class, TestTargetBean.class})
    public static class MyBean {

        @Bean
        public OtherBean otherBean() {
            PrintUtils.errorLog();
            return new OtherBean();
        }


    }


    public static class OtherBean {


    }



    public static class OtherImportBean implements ImportBeanDefinitionRegistrar {
        public OtherImportBean() {
            PrintUtils.errorLog();
        }
    }
}
