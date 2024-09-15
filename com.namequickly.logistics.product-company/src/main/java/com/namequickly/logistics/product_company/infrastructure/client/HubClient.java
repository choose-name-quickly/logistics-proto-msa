package com.namequickly.logistics.product_company.infrastructure.client;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hub-service")
public interface HubClient {

    // 해당 hub 존재 여부 확인
    @GetMapping("/api/hubs")
    boolean checkHubExists(@RequestParam(name = "hubId") UUID hubId);

}
