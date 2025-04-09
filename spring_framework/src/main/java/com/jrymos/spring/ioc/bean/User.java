package com.jrymos.spring.ioc.bean;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public record User(Integer id, String name) implements InitializingBean, DisposableBean {
    public User {
        System.out.println(this + "\t" + "User");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println(this + "\t" + "afterPropertiesSet");
    }

    @PostConstruct
    public void post() {
        System.out.println(this + "\t" + "post");
    }

    @Override
    public void destroy() {
        System.out.println(this + "\t" + "destroy");
    }
}
