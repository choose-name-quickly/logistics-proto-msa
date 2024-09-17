package com.namequickly.logistics.order.infrastructure.client;

import com.namequickly.logistics.order.application.dto.client.HubDto;
import com.namequickly.logistics.order.application.dto.client.HubRouteCourierDto;
import com.namequickly.logistics.order.application.dto.client.HubRouteDto;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hub-service")
public interface HubClient {

    /**
     * routeHubId, courierId, sequence
     */
    @GetMapping("/api/hubs")
    List<HubRouteCourierDto> getHubRoutes(@RequestParam(name = "originHubId") UUID originHubId,
        @RequestParam(name = "destinationHubId") UUID destinationHubId);

    // TODO 허브에게 routeHubId(=startHubId) 로 허브 정보 가져와야함
    @GetMapping("/api/hubs/{hub_id}")
    HubDto getHub(@PathVariable("hub_id") UUID hubId);

    @GetMapping("/api/hub-routes/{route_hub_id}")
    HubRouteDto getHubRoute(@PathVariable("route_hub_id") UUID routeHubId);

    @GetMapping("/api/routes/{route_id}")
    List<HubRouteDto> getHubRoutes(@PathVariable("route_id") UUID routeId);

}
