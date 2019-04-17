package com.define.commons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 基于客户端的负载均衡的轮子使用的说明：
 * 1，
 * 2，用不同的端口启动服务提供者的应用；
 * 3，启动消费者应用；
 * 4，浏览器访问：localhost:9000/ribbon-consumer
 */
@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
@EnableHystrix
public class SpringcloudRibbonApplication {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudRibbonApplication.class, args);
	}
}
