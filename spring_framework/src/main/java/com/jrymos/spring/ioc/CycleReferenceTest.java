package com.jrymos.spring.ioc;

import com.jrymos.spring.ioc.bean.Chicken;
import com.jrymos.spring.ioc.bean.Egg;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class CycleReferenceTest {

    @Test
    public void testEgg() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc/cycle.xml");
        context.getBean(Egg.class);
        context.getBean(Chicken.class);
    }

    @Test
    public void testChicken() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc/cycle.xml");
        context.getBean(Chicken.class);
        context.getBean(Egg.class);
    }
}
