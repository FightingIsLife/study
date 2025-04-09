package com.jrymos.spring.ioc.bean;

import org.springframework.beans.factory.FactoryBean;

public class UserFactoryBean implements FactoryBean<User> {
    @Override
    public User getObject() throws Exception {
        return new User(9, "王九");
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

}
