package com.jrymos.spring.ioc;

import com.jrymos.spring.ioc.bean.Egg;
import com.jrymos.spring.ioc.bean.Hen;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class LookUpTest {
    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc/hen.xml");
        Hen hen = context.getBean(Hen.class);
        List<Egg> eggs = hen.createEggs();
        System.out.println(eggs.get(0) == eggs.get(1));
        System.out.println(hen.createEggs().get(0) == (eggs.get(1)));
    }
}
