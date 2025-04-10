package com.jrymos.spring.ioc.annotation;

public class Cat implements Animal {
    @Override
    public String name() {
        return "cat";
    }
}
