package com.jrymos.spring.ioc.annotation;

public class Dog implements Animal {
    @Override
    public String name() {
        return "dog";
    }
}