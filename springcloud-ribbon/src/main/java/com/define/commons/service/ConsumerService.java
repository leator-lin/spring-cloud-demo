package com.define.commons.service;

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

    public String consumerService(String name) {
        return restTemplate.getForObject("http://hello-service/hi?"+name, String.class);
    }
}
