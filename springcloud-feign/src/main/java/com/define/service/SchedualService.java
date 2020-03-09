package com.define.service;

import com.define.config.FullLogConfiguration;
import com.define.dto.User;
import com.define.service.impl.SchedualServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

//通过实现该接口，并且用@FeignClient注解的fallback属性来指定对应的服务降级类
@FeignClient(value = "eurekaClient-service",
        configuration = FullLogConfiguration.class,
        fallback = SchedualServiceImpl.class)
public interface SchedualService {

    @GetMapping("/feign")
    String feignTest(@RequestParam("name") String name);

    @GetMapping("/feign1")
    User feignTest1(@RequestHeader("name") String name, @RequestHeader("age") Integer age);

    @PostMapping("/feign2")
    String feignTest2(@RequestBody User user);
}