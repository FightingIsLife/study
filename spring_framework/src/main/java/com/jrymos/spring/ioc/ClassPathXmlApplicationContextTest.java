package com.jrymos.spring.ioc;

import com.jrymos.spring.ioc.bean.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClassPathXmlApplicationContextTest {

    @Test
    public void test() {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("ioc/user_service.xml");
        context.start();
        UserService userService = context.getBean("userService", UserService.class);
        System.out.println(userService.getUsers());
        context.registerShutdownHook();
    }

}
