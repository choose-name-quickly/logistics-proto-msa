package com.namequickly.logistics.order.presentation.mapper;

import com.namequickly.logistics.order.application.dto.OrderCreateDto;
import com.namequickly.logistics.order.application.dto.OrderUpdateDto;
import com.namequickly.logistics.order.presentation.dto.OrderCreateRequestDto;
import com.namequickly.logistics.order.presentation.dto.OrderUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    // presentation dto -> application dto 변환
    OrderCreateDto toOrderCreateDto(OrderCreateRequestDto requestDto);

    OrderUpdateDto toOrderUpdateDto(OrderUpdateRequestDto requestDto);

}
