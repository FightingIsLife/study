package com.jrymos.spring.boot.init;

import org.springframework.context.annotation.Bean;

public class SendPushApiConfiguration {

    @Bean
    public MyPushApi myPushApi() {
        return new MyPushApi();
    }

    public static class MyPushApi {

    }
}