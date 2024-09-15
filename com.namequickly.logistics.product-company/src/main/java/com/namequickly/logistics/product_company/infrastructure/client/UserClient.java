package com.namequickly.logistics.product_company.infrastructure.client;

import com.namequickly.logistics.product_company.infrastructure.dto.UserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/users/{user-name}")
    UserInfoDto findUser(@PathVariable("user-name") String userName);
}
