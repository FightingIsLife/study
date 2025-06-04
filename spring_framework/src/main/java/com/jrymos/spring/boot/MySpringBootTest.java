package com.jrymos.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = "com.jrymos.spring.ioc.refresh")
public class MySpringBootTest {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MySpringBootTest.class, args);
    }
}
