package com.jrymos.spring.ioc.annotation;

import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Set;

@Configuration
public class AnimalConfiguration {

    @Bean
    public CustomAutowireConfigurer customAutowireConfigurer() {
        CustomAutowireConfigurer customAutowireConfigurer = new CustomAutowireConfigurer();
        customAutowireConfigurer.setCustomQualifierTypes(Set.of(AnimalQualifier.class.getName()));
        return customAutowireConfigurer;
    }

    @Bean
    @Primary
    @AnimalQualifier(type = AnimalType.CAT)
    public AnimalService<Cat> catAnimalService() {
        return new AnimalService<>(new Cat());
    }

    @Bean
    @AnimalQualifier(type = AnimalType.DOG)
    public AnimalService<Dog> dogAnimalService() {
        return new AnimalService<>(new Dog());
    }


    @Bean
    public String myConfig() {
        return "hello";
    }
}
