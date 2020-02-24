package com.define.commons.controller;

import com.define.commons.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @Author: Lea
 * @Date: 2018/12/2 11:06
 */
@RestController
public class HelloController {

    @Value("${server.port}")
    String port;

    @GetMapping("/hi")
    public String home(@RequestParam(value = "name", defaultValue = "lea") String name) {
        return "hi " + name + " ,i am from port:" + port;
    }

    @GetMapping("/feign")
    public String feignTest(@RequestParam String name) {
        return "hi！" + name;
    }

    @GetMapping("/feign1")
    public User feignTest1(@RequestHeader String name, @RequestHeader Integer age) {
        return new User(name, age);
    }

    @PostMapping("/feign2")
    public String feignTest2(@RequestBody User user) {
        return "Hello " + user.getName() + ", " + user.getAge();
    }
}
