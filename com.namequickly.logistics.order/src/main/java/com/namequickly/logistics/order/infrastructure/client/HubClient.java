package com.namequickly.logistics.order.infrastructure.client;

import com.namequickly.logistics.common.response.CommonResponse;
import com.namequickly.logistics.order.application.dto.client.HubResponseDto;
import com.namequickly.logistics.order.application.dto.client.HubRouteCourierDto;
import com.namequickly.logistics.order.application.dto.client.RouteHubResponseDto;
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
    @GetMapping("/api/hubs/{hubId}")
    CommonResponse<HubResponseDto> getHub(@PathVariable("hubId") UUID hubId);

    @GetMapping("/api/route-hubs/{routeHubId}")
    CommonResponse<RouteHubResponseDto> getRouteHub(@PathVariable("routeHubId") UUID routeHubId);

    /*@GetMapping("/api/routes/{routeId}")
    List<RouteHubResponseDto> getRoute(@PathVariable("routeId") UUID routeId);
*/
}
