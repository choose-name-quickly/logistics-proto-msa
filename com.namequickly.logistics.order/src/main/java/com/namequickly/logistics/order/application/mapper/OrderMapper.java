package com.namequickly.logistics.order.application.mapper;

import com.namequickly.logistics.order.application.dto.OrderCreateResponseDto;
import com.namequickly.logistics.order.application.dto.OrderDeleteResponseDto;
import com.namequickly.logistics.order.application.dto.OrderResponseDto;
import com.namequickly.logistics.order.domain.model.delivery.Delivery;
import com.namequickly.logistics.order.domain.model.delivery.DeliveryRoute;
import com.namequickly.logistics.order.domain.model.order.Order;
import com.namequickly.logistics.order.domain.model.order.OrderProduct;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    // Order에서 Delivery와 DeliveryRoutes를 참조해 자동으로 매핑
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "isDelete", target = "isDelete")
    OrderResponseDto toOrderResponseDto(Order order);

    @Mapping(source = "order.orderId", target = "orderId")
    @Mapping(source = "order.deletedAt", target = "deletedAt")
    OrderDeleteResponseDto toOrderDeleteResponseDto(Order order);

  /*  @Mapping(source = "order.orderId", target = "orderId")
    @Mapping(source = "order.updatedAt", target = "updatedBy")
    @Mapping(source = "orderProduct.productId", target = "productId")
    @Mapping(source = "orderProduct.orderQuantity", target = "orderQuantity")
    OrderUpdateResponseDto toOrderUpdateResponseDto(Order order, OrderProduct orderProduct);
*/

    List<OrderResponseDto.OrderProductDto> toOrderProductDtos(List<OrderProduct> orderProducts);

    @Mapping(source = "isDelete", target = "isDelete")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(target = "deliveryRoutes", source = "deliveryRoutes")
    OrderResponseDto.DeliveryDto toDeliveryDto(Delivery delivery);

    @Mapping(source = "isDelete", target = "isDelete")
    List<OrderResponseDto.DeliveryRouteDto> toDeliveryRouteDtos(List<DeliveryRoute> deliveryRoutes);

    OrderCreateResponseDto toOrderCreateResponseDto(Order order);
}