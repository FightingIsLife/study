package com.jrymos.spring.ioc.bean;

import javax.annotation.PostConstruct;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

@Getter
@Setter
public class UserService implements InitializingBean, DisposableBean {
    private List<User> users;

    public UserService() {
        System.out.println(this + "\t" + "UserService");
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
