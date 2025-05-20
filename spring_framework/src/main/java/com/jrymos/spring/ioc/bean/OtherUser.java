package com.jrymos.spring.ioc.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OtherUser {
    @Value("${test}")
    private String value;
}
