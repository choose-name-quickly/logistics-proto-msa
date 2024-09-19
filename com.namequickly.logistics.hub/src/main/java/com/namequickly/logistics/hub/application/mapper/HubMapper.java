package com.namequickly.logistics.hub.application.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.namequickly.logistics.hub.application.dto.HubRequestDto;
import com.namequickly.logistics.hub.application.dto.HubResponseDto;
import com.namequickly.logistics.hub.domain.model.Hub;
import java.util.List;
import org.mapstruct.Mapper;


@Mapper(componentModel = SPRING)
public interface HubMapper {
    HubResponseDto toDTO(Hub hub);
    Hub toEntity(HubRequestDto hubDTO);

    List<HubResponseDto> toDTOs(List<Hub> hubs);
    List<Hub> toEntities(List<HubRequestDto> hubDTOs);
}
