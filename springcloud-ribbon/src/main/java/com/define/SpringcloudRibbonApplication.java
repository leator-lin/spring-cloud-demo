package com.define;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 客户端负载均衡的实现说明：
 * 1，服务提供者启动多个服务实例；
 * 2，实现负载均衡最重要的两步，在消费者应用中：
 * 	2.1，通过@EnableDiscoveryClient注解让该应用注册为
 * 		Eureka客户端应用，以获得服务发现的能力
 * 	2.2，创建RestTemplate的Spring Bean实例，并通过
 * 		@LoadBalanced注解开启客户端负载均衡
 */

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
