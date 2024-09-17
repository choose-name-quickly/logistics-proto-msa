package com.namequickly.logistics.product_company.infrastructure.client;

import com.namequickly.logistics.product_company.application.dto.client.HubDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hub-service")
public interface HubClient {

    // TODO 허브에게 routeHubId(=startHubId) 로 허브 정보 가져와야함
    @GetMapping("/api/hubs/{hub_id}")
    HubDto getHub(@PathVariable("hub_id") UUID hubId);

}
