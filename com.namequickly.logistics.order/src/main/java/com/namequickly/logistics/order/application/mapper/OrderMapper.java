package com.namequickly.logistics.order.application.mapper;

import com.namequickly.logistics.order.application.dto.OrderResponseDto;
import com.namequickly.logistics.order.domain.model.delivery.Delivery;
import com.namequickly.logistics.order.domain.model.delivery.DeliveryRoute;
import com.namequickly.logistics.order.domain.model.order.Order;
import com.namequickly.logistics.order.domain.model.order.OrderProduct;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    // Order에서 Delivery와 DeliveryRoutes를 참조해 자동으로 매핑
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "isDelete", target = "isDelete")
    OrderResponseDto toOrderResponseDto(Order order);

    List<OrderResponseDto.OrderProductDto> toOrderProductDtos(List<OrderProduct> orderProducts);

    @Mapping(source = "isDelete", target = "isDelete")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(target = "deliveryRoutes", source = "deliveryRoutes")
    OrderResponseDto.DeliveryDto toDeliveryDto(Delivery delivery);

    @Mapping(source = "isDelete", target = "isDelete")
    List<OrderResponseDto.DeliveryRouteDto> toDeliveryRouteDtos(List<DeliveryRoute> deliveryRoutes);

}