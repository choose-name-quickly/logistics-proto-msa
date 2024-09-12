package com.namequickly.logistics.hub.application.service;

import com.namequickly.logistics.common.exception.GlobalException;
import com.namequickly.logistics.common.response.ResultCase;
import com.namequickly.logistics.hub.application.dto.RouteRequestDto;
import com.namequickly.logistics.hub.application.dto.RouteResponseDto;
import com.namequickly.logistics.hub.application.mapper.RouteMapper;
import com.namequickly.logistics.hub.domain.model.Route;
import com.namequickly.logistics.hub.domain.repository.RouteRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
