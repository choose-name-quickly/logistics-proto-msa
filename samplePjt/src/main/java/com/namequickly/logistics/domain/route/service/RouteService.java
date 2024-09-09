package com.namequickly.logistics.domain.route.service;

import com.namequickly.logistics.domain.route.domain.Route;
import com.namequickly.logistics.domain.route.dto.RouteRequestDto;
import com.namequickly.logistics.domain.route.dto.RouteResponseDto;
import com.namequickly.logistics.domain.route.mapper.RouteMapper;
import com.namequickly.logistics.domain.route.repository.RouteRepository;
import com.namequickly.logistics.global.exception.GlobalException;
import com.namequickly.logistics.global.common.response.ResultCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;

    public RouteResponseDto createRoute(RouteRequestDto requestDto) {
        Route route = Route.createRoute(requestDto);
        Route savedRoute = routeRepository.save(route);
        return routeMapper.toDTO(savedRoute);
    }

    public RouteResponseDto updateRoute(UUID routeId, RouteRequestDto requestDto) {
        Route route = routeRepository.findById(routeId)
            .orElseThrow(() -> new GlobalException(ResultCase.NOT_FOUND));
        route.updateRoute(requestDto);
        Route updatedRoute = routeRepository.save(route);
        return routeMapper.toDTO(updatedRoute);
    }

    public void deleteRoute(UUID routeId) {
        Route route = routeRepository.findById(routeId)
            .orElseThrow(() -> new GlobalException(ResultCase.NOT_FOUND));
        routeRepository.delete(route);
    }

    public RouteResponseDto getRoute(UUID routeId) {
        Route route = routeRepository.findById(routeId)
            .orElseThrow(() -> new GlobalException(ResultCase.NOT_FOUND));
        return routeMapper.toDTO(route);
    }

    public List<RouteResponseDto> listRoutes() {
        List<Route> routes = routeRepository.findAll();
        return routeMapper.toDTOs(routes);
    }
}
