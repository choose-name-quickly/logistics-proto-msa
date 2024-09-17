package com.namequickly.logistics.hub.presentation.controller;

import com.namequickly.logistics.common.response.CommonResponse;
import com.namequickly.logistics.common.response.CommonResponse.CommonEmptyRes;
import com.namequickly.logistics.hub.application.dto.RouteHubRequestDto;
import com.namequickly.logistics.hub.application.dto.RouteHubResponseDto;
import com.namequickly.logistics.hub.application.service.RouteHubService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/route-hubs")
@RequiredArgsConstructor
public class RouteHubController {

    private final RouteHubService routeHubService;

    @PostMapping
    public CommonResponse<RouteHubResponseDto> createRouteHub(@RequestBody RouteHubRequestDto requestDto) {
        RouteHubResponseDto routeHub = routeHubService.createRouteHub(requestDto);
        return CommonResponse.success(routeHub);
    }

    @PutMapping("/{routeHubId}")
    public CommonResponse<RouteHubResponseDto> updateRouteHub(
        @PathVariable("routeHubId") UUID routeHubId,
        @RequestBody RouteHubRequestDto requestDto) {
        RouteHubResponseDto routeHub = routeHubService.updateRouteHub(routeHubId, requestDto);
        return CommonResponse.success(routeHub);
    }

    @DeleteMapping("/{routeHubId}")
    public CommonResponse<CommonEmptyRes> deleteRouteHub(@PathVariable("routeHubId") UUID routeHubId) {
        routeHubService.deleteRouteHub(routeHubId);
        return CommonResponse.success();
    }

    @GetMapping("/{routeHubId}")
    public CommonResponse<RouteHubResponseDto> getRouteHub(@PathVariable("routeHubId") UUID routeHubId) {
        RouteHubResponseDto routeHub = routeHubService.getRouteHub(routeHubId);
        return CommonResponse.success(routeHub);
    }

    @GetMapping
    public CommonResponse<List<RouteHubResponseDto>> listRouteHubs() {
        List<RouteHubResponseDto> routeHubs = routeHubService.listRouteHubs();
        return CommonResponse.success(routeHubs);
    }
}
