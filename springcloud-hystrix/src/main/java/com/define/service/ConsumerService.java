package com.define.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @Author: Lea
 * @Date: 2018/11/29 22:44
 */
@Service
public class ConsumerService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiFallback")
    public String consumerService(String name) {
        return restTemplate.getForObject("http://eurekaClient-service/hi?"+name, String.class);
    }

    public String hiFallback(String name) {
        return "error " + name;
    }
}
