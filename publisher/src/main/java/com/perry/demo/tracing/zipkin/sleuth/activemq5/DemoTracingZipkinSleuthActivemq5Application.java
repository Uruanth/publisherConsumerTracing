package com.perry.demo.tracing.zipkin.sleuth.activemq5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;

@SpringBootApplication(exclude = JmsAutoConfiguration.class)
public class DemoTracingZipkinSleuthActivemq5Application {

	public static void main(String[] args) {
		SpringApplication.run(DemoTracingZipkinSleuthActivemq5Application.class, args);
	}

}
