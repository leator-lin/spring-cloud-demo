package com.define.commons.controller;

import com.define.commons.util.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public R feignTest() {
        return new R(200, "测试feign");
    }
}
