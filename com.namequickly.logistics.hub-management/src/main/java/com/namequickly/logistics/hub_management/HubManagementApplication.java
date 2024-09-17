package com.namequickly.logistics.hub_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HubManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HubManagementApplication.class, args);
	}
}
