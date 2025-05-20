package com.jrymos.spring.ioc;

import com.jrymos.spring.ioc.refresh.EarlyApplicationListener;
import com.jrymos.spring.ioc.refresh.TestBeanDefinitionRegistryPostProcessor;
import com.jrymos.spring.ioc.refresh.TestBeanFactoryProcessor;
import com.jrymos.spring.ioc.refresh.ifc.TestInterface;
import org.junit.jupiter.api.Test;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;


public class SpringRefreshTest {

    @Test
    public void testSpringRefresh() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.addApplicationListener(new EarlyApplicationListener());
        context.addBeanFactoryPostProcessor(new TestBeanFactoryProcessor());
        context.addBeanFactoryPostProcessor(new TestBeanDefinitionRegistryPostProcessor());
        context.scan(EarlyApplicationListener.class.getPackageName());
        context.refresh();
        TestInterface bean = context.getBean(TestInterface.class);
        System.out.println(bean.myTest());
    }
}
