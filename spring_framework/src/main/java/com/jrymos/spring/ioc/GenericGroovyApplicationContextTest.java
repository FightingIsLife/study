package com.jrymos.spring.ioc;

import com.jrymos.spring.ioc.bean.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;

public class GenericGroovyApplicationContextTest {
    @Test
    public void test1() {
        ApplicationContext context = new GenericGroovyApplicationContext("ioc/user.groovy");
        System.out.println(context.getBean("user5"));
    }

    @Test
    public void test2() {
        GenericApplicationContext context = new GenericApplicationContext();
        new XmlBeanDefinitionReader(context).loadBeanDefinitions("ioc/user.xml");
        new GroovyBeanDefinitionReader(context).loadBeanDefinitions("ioc/user.groovy", "ioc/user_service.groovy");
        System.out.println("-----------");
        context.refresh();
        System.out.println("-----------");
        System.out.println(context.getBean("userService", UserService.class).getUsers());
    }
}
