package com.namequickly.logistics.domain.hub.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.namequickly.logistics.domain.hub.domain.Hub;
import com.namequickly.logistics.domain.hub.dto.HubRequestDto;
import com.namequickly.logistics.domain.hub.dto.HubResponseDto;
import java.util.List;
import org.mapstruct.Mapper;


@Mapper(componentModel = SPRING)
public interface HubMapper {
    HubResponseDto toDTO(Hub hub);
    Hub toEntity(HubRequestDto hubDTO);
    List<HubResponseDto> toDTOs(List<Hub> hubs);
}
