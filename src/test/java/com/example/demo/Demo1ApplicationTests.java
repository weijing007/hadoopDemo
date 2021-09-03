package com.example.demo;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo1ApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(Demo1ApplicationTests.class);

	@Test
	void contextLoads() {
	}
	
	
	public static void main(String[] args) {
		logger.warn("start success 11 !");
		logger.info("start success 12 !");
		logger.debug("start success 13 !");
		logger.error("start success 14 !");
		System.out.println("start success 15 !");
		
	}
	
	

}
