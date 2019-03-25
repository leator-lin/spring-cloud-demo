package com.define.commons.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public String feignTest(String name) {
        return "hi！" + name;
    }
}
