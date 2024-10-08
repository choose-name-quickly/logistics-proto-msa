package com.namequickly.logistics.product_company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication//(exclude = SecurityAutoConfiguration.class) // 개발위해 시큐리티 임시 비활성화
public class ProductCompanyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductCompanyApplication.class, args);
    }

}
