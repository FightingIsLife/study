package com.jrymos.spring.ioc.annotation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
public class TestService {
    private final AnimalService<Cat> catAnimalService;
    @Autowired
    private AnimalService<Dog> dogAnimalService;

    @Autowired
    @Qualifier("dogAnimalService")
    private AnimalService animalService;

    @Autowired
    @AnimalQualifier(type = AnimalType.DOG)
    private AnimalService dog;

    @Autowired
    @AnimalQualifier(type = AnimalType.CAT)
    private AnimalService cat;

    @Value("${test}")
    private String value;
}
