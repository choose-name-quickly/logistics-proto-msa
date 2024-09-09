package com.namequickly.logistics.domain.route.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.namequickly.logistics.domain.route.domain.Route;
import com.namequickly.logistics.domain.route.dto.RouteResponseDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface RouteMapper {
    RouteResponseDto toDTO(Route route);
    Route toEntity(RouteResponseDto routeDTO);
    List<RouteResponseDto> toDTOs(List<Route> routes);
}