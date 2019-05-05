package com.define.controller;

import com.define.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public String helloConsumer(@RequestParam(value = "name") String name) {
        return consumerService.consumerService(name);
    }
}
