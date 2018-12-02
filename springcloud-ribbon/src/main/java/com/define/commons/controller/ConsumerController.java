package com.define.commons.controller;

import com.define.commons.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * xxx
 *
 * @Author: Lea
 * @Date: 2018/11/1 15:31
 */
@RestController
public class ConsumerController {
    /*@Autowired
    RestTemplate restTemplate;*/
    @Autowired
    ConsumerService consumerService;

    @RequestMapping(value = "/ribbon-consumer", method = RequestMethod.GET)
    public String helloConsumer(String name) {
        /*return restTemplate.getForEntity("http://hello-service/hello", String.class).getBody();*/
        return consumerService.consumerService(name);
    }
}
