package com.define.commons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableAutoConfiguration(exclude = org.activiti.spring.boot.SecurityAutoConfiguration.class)
public class SpringcloudActivitiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudActivitiApplication.class, args);
	}
}
