package com.namequickly.logistics.order.application.mapper;

import com.namequickly.logistics.order.application.dto.DeliveryGetResponseDto;
import com.namequickly.logistics.order.application.dto.DeliveryGetResponseDto.DeliveryRouteDto;
import com.namequickly.logistics.order.domain.model.delivery.Delivery;
import com.namequickly.logistics.order.domain.model.delivery.DeliveryRoute;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    @Mapping(source = "delivery.deliveryId", target = "deliveryId")
    @Mapping(source = "delivery.order.orderId", target = "orderId")
    @Mapping(source = "delivery.originHubId", target = "originHubId")
    @Mapping(source = "delivery.destinationHubId", target = "destinationHubId")
    @Mapping(source = "delivery.recipientName", target = "recipientName")
    @Mapping(source = "delivery.recipientSlackId", target = "recipientSlackID")
    @Mapping(source = "delivery.deliveryStatus", target = "deliveryStatus")
    @Mapping(source = "delivery.createdAt", target = "createdAt")
    @Mapping(source = "delivery.updatedAt", target = "updatedAt")
    @Mapping(source = "delivery.deliveryRoutes", target = "deliveryRoutes")
    DeliveryGetResponseDto toDeliveryGetResponseDto(Delivery delivery);

    // DeliveryRoute -> DeliveryRouteDto 매핑
    @Mapping(source = "deliveryRoute.deliveryRouteId", target = "deliveryRouteId")
    @Mapping(source = "deliveryRoute.routeHubId", target = "routeHubId")
    @Mapping(source = "deliveryRoute.courierId", target = "courierId")
    @Mapping(source = "deliveryRoute.actualDistance", target = "actualDistance")
    @Mapping(source = "deliveryRoute.actualDuration", target = "actualTime")
    @Mapping(source = "deliveryRoute.deliveryStatus", target = "deliveryStatus")
    @Mapping(source = "deliveryRoute.createdAt", target = "createdAt")
    @Mapping(source = "deliveryRoute.updatedAt", target = "updatedAt")
    DeliveryRouteDto toDeliveryRouteDto(DeliveryRoute deliveryRoute);

    // List<DeliveryRoute> -> List<DeliveryRouteDto> 매핑
    List<DeliveryRouteDto> toDeliveryRouteDtoList(List<DeliveryRoute> deliveryRoutes);

}