package com.namequickly.logistics.slack_message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SlackMessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlackMessageApplication.class, args);
	}

}
