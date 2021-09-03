package com.weijin.hadoopdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		logger.warn("start success 1 !");
		logger.info("start success 2 !");
		logger.debug("start success 3 !");
		logger.error("start success 4 !");
		System.out.println("start success 5 !");
	}

}
