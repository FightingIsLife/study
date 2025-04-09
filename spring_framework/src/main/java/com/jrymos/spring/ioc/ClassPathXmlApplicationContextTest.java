package com.jrymos.spring.ioc;

import com.jrymos.spring.ioc.bean.UserFactoryBean;
import com.jrymos.spring.ioc.bean.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClassPathXmlApplicationContextTest {

    @Test
    public void test() {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("ioc/user_service.xml");
        context.start();
        UserService userService = context.getBean("userService", UserService.class);
        System.out.println(userService.getUsers());
        UserFactoryBean bean = context.getBean("&user9", UserFactoryBean.class);
        System.out.println(bean);
        context.registerShutdownHook();
    }

}
