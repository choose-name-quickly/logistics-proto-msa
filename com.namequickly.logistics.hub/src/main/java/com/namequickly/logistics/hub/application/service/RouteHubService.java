package com.namequickly.logistics.hub.application.service;

import com.namequickly.logistics.hub.application.dto.RouteHubResponseDto;
import com.namequickly.logistics.hub.domain.model.RouteHub;
import com.namequickly.logistics.hub.domain.repository.RouteHubRepository;
import com.namequickly.logistics.hub.presentation.request.RouteHubRequest;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RouteHubService {

    private final RouteHubRepository routeHubRepository;
    private final RouteHubMapper routeHubMapper;

    public RouteHubResponseDto createRouteHub(RouteHubRequest requestDto) {
        RouteHub routeHub = RouteHub.createRouteHub(requestDto);
        RouteHub savedRouteHub = routeHubRepository.save(routeHub);
        return routeHubMapper.toDTO(savedRouteHub);
    }

    public RouteHubResponseDto updateRouteHub(UUID routeHubId, RouteHubRequest requestDto) {
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
