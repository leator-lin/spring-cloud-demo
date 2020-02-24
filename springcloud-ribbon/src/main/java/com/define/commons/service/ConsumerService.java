package com.define.commons.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 *
 * @Author: Lea
 * @Date: 2018/11/29 22:44
 */
@Service
public class ConsumerService {
    private static final Log logger = LogFactory.getLog(ConsumerService.class);

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "consmerFallback")
    public String consumerService(String name) throws Exception {
        int sleepTime = new Random().nextInt(3000);
        logger.info("sleepTime" + sleepTime);
        Thread.sleep(sleepTime);

        return restTemplate.getForObject("http://eurekaClient-service/hi?name="+name, String.class);
    }

    // 这里有个很坑的问题：当映射的接口参数和服务降级处理的方法参数不一致时，
    // 比如上面服务的方法是一个字符串参数，服务降级处理方法也必须一个字符串参数，如果不一致
    // 就会报错：hystrix.contrib.javanica.exception.FallbackDefinitionException: fallback method wasn't found
    public String consmerFallback(String name) {
        return "服务出现错误，请进行检查！！！" + name;
    }
}
