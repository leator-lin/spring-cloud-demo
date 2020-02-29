package com.define;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration(exclude = org.activiti.spring.boot.SecurityAutoConfiguration.class)
public class SpringcloudActivitiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudActivitiApplication.class, args);
	}
}
