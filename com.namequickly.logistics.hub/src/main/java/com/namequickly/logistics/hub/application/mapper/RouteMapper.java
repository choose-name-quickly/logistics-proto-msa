package com.namequickly.logistics.hub.application.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.namequickly.logistics.hub.application.dto.RouteResponseDto;
import com.namequickly.logistics.hub.domain.model.Route;
import com.namequickly.logistics.hub.domain.model.RouteHub;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface RouteMapper {
    RouteResponseDto toDTO(Route route);
    Route toEntity(RouteResponseDto routeDTO);
    List<RouteResponseDto> toDTOs(List<Route> routes);

    // 경로와 경유 허브 정보를 담은 DTO로 변환
    RouteResponseDto toResponseDto(Route route, List<RouteHub> routes);
}