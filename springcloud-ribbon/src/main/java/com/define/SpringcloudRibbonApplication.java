package com.define;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 基于客户端的负载均衡的轮子使用的说明：
 * 1，启动注册中心；
 * 2，用不同的端口启动服务提供者的应用；
 * 3，启动消费者应用：
 * 	3.1，通过@EnableDiscoveryClient注解让该应用注册为
 * 		Eureka客户端应用，以获得服务发现的能力
 * 	3.2，创建RestTemplate的Spring Bean实例，并通过
 * 		@LoadBalanced注解开启客户端负载均衡
 */
//使用@EnableCircuitBreaker开启断路器功能
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
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
