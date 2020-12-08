package com.define.service;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PersonService {

    @PostConstruct
    public void init() {
        System.out.println("@PostConstruct-------------------------------------------");
    }
}
