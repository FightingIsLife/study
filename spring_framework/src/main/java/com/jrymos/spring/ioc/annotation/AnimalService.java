package com.jrymos.spring.ioc.annotation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@RequiredArgsConstructor
public class AnimalService<T extends Animal> implements InitializingBean, DisposableBean {

    private final T t;

    @Value("${test}")
    private String value;


    public void print() {
        System.out.println(this + "\t" + t + "\t" + t.name());
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy" + "\t" + t + "\t" + t.name() + "\t" + value);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet" + "\t" + t + "\t" + t.name() + "\t" + value);
    }


    @PostConstruct
    public void init() {
        System.out.println("init" + "\t" + t + "\t" + t.name() + "\t" + value);
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("preDestroy" + "\t" + t + "\t" + t.name() + "\t" + value);
    }
}
