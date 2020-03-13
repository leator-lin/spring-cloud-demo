package com.define.springcloudmybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 使用mybatis-plus的步骤：
 * 1，导入mybatis-plus的依赖；
 * 2，在主类上加上注解@MapperScan；
 */
@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.define.springcloudmybatisplus.dao")
public class SpringcloudMybatisPlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudMybatisPlusApplication.class, args);
	}

}
