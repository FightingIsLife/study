package com.jrymos.spring.ioc.bean;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.Lifecycle;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public record User(Integer id, String name) implements InitializingBean, DisposableBean, Lifecycle {
    public User {
        System.out.println(this + "\t" + "User");
    }


    public void init() {
        System.out.println(this + "\t" + "init");
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

    @Override
    public void start() {
        System.out.println(this + "\t" + "start");
    }

    @Override
    public void stop() {
        System.out.println(this + "\t" + "stop");
        stops.add(this);
    }

    @Override
    public boolean isRunning() {
        return !stops.contains(this);
    }

    static Set<User> stops = new CopyOnWriteArraySet<>();
}
