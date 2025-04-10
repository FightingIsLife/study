package com.jrymos.spring.ioc;

import com.jrymos.spring.ioc.annotation.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class AnnotationApplicationContextTest {

    @Test
    public void test() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.scan(TestService.class.getPackageName());
        context.refresh();
        TestService testService = context.getBean(TestService.class);
        testService.getAnimalService().print();
        testService.getDogAnimalService().print();
        testService.getCatAnimalService().print();
        testService.getCat().print();
        testService.getDog().print();
        System.out.println(testService.getValue());
        context.registerShutdownHook();
    }
}
