package com.define;

import com.sun.istack.internal.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class SpringcloudSleuthService2Application {

	private final Logger logger = Logger.getLogger(getClass());

	@GetMapping("/trace-2")
	public String trace() {
		logger.info("===call trace-2");
		return "Trace";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudSleuthService2Application.class, args);
	}

}
