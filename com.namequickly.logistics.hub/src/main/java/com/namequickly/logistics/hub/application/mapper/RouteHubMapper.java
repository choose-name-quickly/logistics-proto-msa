package com.namequickly.logistics.hub.application.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.namequickly.logistics.hub.application.dto.RouteHubRequestDto;
import com.namequickly.logistics.hub.application.dto.RouteHubResponseDto;
import com.namequickly.logistics.hub.domain.model.RouteHub;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface RouteHubMapper {
    RouteHubResponseDto toDTO(RouteHub routeHub);
    RouteHub toEntity(RouteHubRequestDto routeHubDTO);
    List<RouteHubResponseDto> toDTOs(List<RouteHub> routeHubs);
}
