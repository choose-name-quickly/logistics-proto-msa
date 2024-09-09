package com.namequickly.logistics.product_company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ProductCompanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductCompanyApplication.class, args);
	}

}
