package com.define.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 1，将配置中心和客户端都注册到服务注册中心上面，并配置消息总线；
 * 2，测试消息总线的功能，使用localhost:端口号/actuator/bus-refresh发送post请求；
 * 3，
 *
 * @Author: Lea
 * @Date: 2019/4/22 21:36
 */
@RefreshScope
@RestController
public class TestController {

    @Value("${from}")
    private String from;

    @Autowired
    private Environment env;

    @RequestMapping("/from")
    public String from() {
        return this.from;
    }

    @RequestMapping("/to")
    public String to() {
        return env.getProperty("from", "undefined");
    }
}
