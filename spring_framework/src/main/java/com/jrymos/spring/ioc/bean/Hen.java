package com.jrymos.spring.ioc.bean;


import java.util.List;

/**
 * 下蛋母鸡
 */
public abstract class Hen extends Chicken {


    public List<Egg> createEggs() {
        return List.of(createEgg(), createEgg());
    }

//    @Lookup
    public abstract Egg createEgg();
}
