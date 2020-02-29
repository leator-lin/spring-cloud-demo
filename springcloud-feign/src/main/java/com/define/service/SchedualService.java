package com.define.service;

import com.define.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "eurekaClient-service")
public interface SchedualService {

    @GetMapping("/feign")
    String feignTest(@RequestParam("name") String name);

    @GetMapping("/feign1")
    User feignTest1(@RequestHeader("name") String name, @RequestHeader("age") Integer age);

    @PostMapping("/feign2")
    String feignTest2(@RequestBody User user);
}