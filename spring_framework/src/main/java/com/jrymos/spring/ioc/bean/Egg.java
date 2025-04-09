package com.jrymos.spring.ioc.bean;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;

@Data
public class Egg implements InitializingBean {
    private Chicken chicken;

    public Egg() {
        System.out.println(this.getClass());
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("afterPropertiesSet\t" + this.getClass());
    }
}
