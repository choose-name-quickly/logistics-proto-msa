package com.namequickly.logistics.domain.routeHub.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.namequickly.logistics.domain.routeHub.domain.RouteHub;
import com.namequickly.logistics.domain.routeHub.dto.RouteHubRequestDto;
import com.namequickly.logistics.domain.routeHub.dto.RouteHubResponseDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = SPRING)
public interface RouteHubMapper {
    RouteHubResponseDto toDTO(RouteHub routeHub);
    RouteHub toEntity(RouteHubRequestDto routeHubDTO);
    List<RouteHubResponseDto> toDTOs(List<RouteHub> routeHubs);
}
