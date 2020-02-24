package com.define.commons.controller;

import com.define.commons.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * xxx
 *
 * @Author: Lea
 * @Date: 2018/11/1 15:31
 */
@RestController
public class ConsumerController {

    @Autowired
    ConsumerService consumerService;

    @GetMapping("/hi")
    public String helloConsumer(@RequestParam(value = "name") String name) throws Exception {
        return consumerService.consumerService(name);
    }
}
