package com.namequickly.logistics.hub.presentation.controller;

import com.namequickly.logistics.common.response.CommonResponse;
import com.namequickly.logistics.common.response.CommonResponse.CommonEmptyRes;
import com.namequickly.logistics.hub.application.dto.RouteRequestDto;
import com.namequickly.logistics.hub.application.dto.RouteResponseDto;
import com.namequickly.logistics.hub.application.service.RouteService;
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
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    public CommonResponse<RouteResponseDto> createRoute(@RequestBody RouteRequestDto requestDto) {
        RouteResponseDto route = routeService.createRoute(requestDto);
        return CommonResponse.success(route);
    }

    @PutMapping("/{routeId}")
    public CommonResponse<RouteResponseDto> updateRoute(
        @PathVariable("routeId") UUID routeId,
        @RequestBody RouteRequestDto requestDto) {
        RouteResponseDto route = routeService.updateRoute(routeId, requestDto);
        return CommonResponse.success(route);
    }

    @DeleteMapping("/{routeId}")
    public CommonResponse<CommonEmptyRes> deleteRoute(@PathVariable("routeId") UUID routeId) {
        routeService.deleteRoute(routeId);
        return CommonResponse.success();
    }

    @GetMapping("/{routeId}")
    public CommonResponse<RouteResponseDto> getRoute(@PathVariable("routeId") UUID routeId) {
        RouteResponseDto route = routeService.getRoute(routeId);
        return CommonResponse.success(route);
    }

    @GetMapping
    public CommonResponse<List<RouteResponseDto>> listRoutes() {
        List<RouteResponseDto> routes = routeService.listRoutes();
        return CommonResponse.success(routes);
    }
}
