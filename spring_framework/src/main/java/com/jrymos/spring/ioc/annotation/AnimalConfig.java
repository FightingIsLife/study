package com.jrymos.spring.ioc.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:ioc/animal.yml")
public class AnimalConfig {
}