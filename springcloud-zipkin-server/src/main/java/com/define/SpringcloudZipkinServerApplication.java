package com.define;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableZipkinServer
@SpringBootApplication
public class SpringcloudZipkinServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudZipkinServerApplication.class, args);
	}

}
