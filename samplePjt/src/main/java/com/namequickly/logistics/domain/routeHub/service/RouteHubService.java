package com.namequickly.logistics.domain.routeHub.service;

import com.namequickly.logistics.domain.routeHub.domain.RouteHub;
import com.namequickly.logistics.domain.routeHub.dto.RouteHubRequestDto;
import com.namequickly.logistics.domain.routeHub.dto.RouteHubResponseDto;
import com.namequickly.logistics.domain.routeHub.mapper.RouteHubMapper;
import com.namequickly.logistics.domain.routeHub.repository.RouteHubRepository;
import com.namequickly.logistics.global.exception.GlobalException;
import com.namequickly.logistics.global.common.response.ResultCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteHubService {

    private final RouteHubRepository routeHubRepository;
    private final RouteHubMapper routeHubMapper;

    public RouteHubResponseDto createRouteHub(RouteHubRequestDto requestDto) {
        RouteHub routeHub = RouteHub.createRouteHub(requestDto);
        RouteHub savedRouteHub = routeHubRepository.save(routeHub);
        return routeHubMapper.toDTO(savedRouteHub);
    }

    public RouteHubResponseDto updateRouteHub(UUID routeHubId, RouteHubRequestDto requestDto) {
        RouteHub routeHub = routeHubRepository.findById(routeHubId)
            .orElseThrow(() -> new GlobalException(ResultCase.NOT_FOUND));
        routeHub.updateRouteHub(requestDto);
        RouteHub updatedRouteHub = routeHubRepository.save(routeHub);
        return routeHubMapper.toDTO(updatedRouteHub);
    }

    public void deleteRouteHub(UUID routeHubId) {
        RouteHub routeHub = routeHubRepository.findById(routeHubId)
            .orElseThrow(() -> new GlobalException(ResultCase.NOT_FOUND));
        routeHubRepository.delete(routeHub);
    }

    public RouteHubResponseDto getRouteHub(UUID routeHubId) {
        RouteHub routeHub = routeHubRepository.findById(routeHubId)
            .orElseThrow(() -> new GlobalException(ResultCase.NOT_FOUND));
        return routeHubMapper.toDTO(routeHub);
    }

    public List<RouteHubResponseDto> listRouteHubs() {
        List<RouteHub> routeHubs = routeHubRepository.findAll();
        return routeHubMapper.toDTOs(routeHubs);
    }
}
