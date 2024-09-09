package com.namequickly.logistics.domain.route.controller;

import com.namequickly.logistics.domain.route.dto.RouteRequestDto;
import com.namequickly.logistics.domain.route.dto.RouteResponseDto;
import com.namequickly.logistics.domain.route.service.RouteService;
import com.namequickly.logistics.global.common.response.CommonResponse;
import com.namequickly.logistics.global.common.response.CommonResponse.CommonEmptyRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
