package com.define;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@MapperScan("com.define.dao")
@EnableEurekaClient
@SpringBootApplication
public class SpringcloudMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudMybatisApplication.class, args);
	}

}
