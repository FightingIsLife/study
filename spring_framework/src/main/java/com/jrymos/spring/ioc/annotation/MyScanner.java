package com.jrymos.spring.ioc.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.jrymos.spring.ioc.bean")
public class MyScanner {
}
