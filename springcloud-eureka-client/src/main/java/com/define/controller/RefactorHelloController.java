package com.define.controller;

import com.define.dto.User;
import com.define.service.RefactorService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用Spring Cloud Feign的继承特性实现REST接口定义的复用
 */
@RestController
public class RefactorHelloController implements RefactorService {

    @Override
    public String feignTest3(@RequestParam("name") String name) {
        return "Hello " + name;
    }

    @Override
    public User feignTest4(@RequestHeader("name") String name, @RequestHeader("age") Integer age) {
        return new User(name, age);
    }

    @Override
    public String feignTest5(@RequestBody User user) {
        return "Hello " + user.getName() + ", " + user.getAge();
    }
}
