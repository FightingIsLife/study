package com.jrymos.spring.ioc.bean;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;

@Data
public class Chicken implements InitializingBean {
    private Egg egg;


    public Chicken() {
        System.out.println(this.getClass());
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("afterPropertiesSet\t" + this.getClass());
    }
}
