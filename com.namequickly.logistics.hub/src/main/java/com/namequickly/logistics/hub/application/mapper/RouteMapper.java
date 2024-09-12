package com.namequickly.logistics.hub.application.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.namequickly.logistics.hub.application.dto.RouteResponseDto;
import com.namequickly.logistics.hub.domain.model.Route;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface RouteMapper {
    RouteResponseDto toDTO(Route route);
    Route toEntity(RouteResponseDto routeDTO);
    List<RouteResponseDto> toDTOs(List<Route> routes);
}