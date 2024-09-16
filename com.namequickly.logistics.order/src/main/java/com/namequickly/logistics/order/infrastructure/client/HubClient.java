package com.namequickly.logistics.order.infrastructure.client;

import com.namequickly.logistics.order.application.dto.HubRouteDto;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hub-service")
public interface HubClient {

    /**
     * routeHubId, courierId, sequence
     */
    @GetMapping("/api/hubs")
    List<HubRouteDto> getHubRoutes(@RequestParam(name = "originHubId") UUID originHubId,
        @RequestParam(name = "destinationHubId") UUID destinationHubId);

    // 해당 hub 존재 여부 확인
    @GetMapping("/api/hubs")
    boolean checkHubExists(@RequestParam(name = "hubId") UUID hubId);
}
