package com.jrymos.spring.ioc.bean;


public class UserFactory {

    public static User createInstance(Integer id, String name) {
        return new User(id, name);
    }
}
