package com.namequickly.logistics.order.presentation.dto;

import com.namequickly.logistics.order.application.dto.OrderCreateDto;
import com.namequickly.logistics.order.application.dto.OrderCreateDto.OrderProductDto;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class OrderCreateRequestDto {

    // 주문
    private final UUID supplierId;
    private final UUID receiverId;

    // 주문 상품
    private final List<OrderProductRequestDto> products;

    // 배송
    private final UUID originHubId;
    private final UUID destinationHubId;
    private final String recipientName;
    private final String recipientSlackId; //휴대폰 번호

    @Getter
    @AllArgsConstructor
    public static class OrderProductRequestDto {

        private final UUID productId; // 외부 상품 id
        private final Integer orderQuantity; // 주문 상품
    }

    public OrderCreateDto toDTO() {
        List<OrderProductDto> productDtos = products.stream()
            .map(product -> new OrderCreateDto.OrderProductDto(product.getProductId(),
                product.getOrderQuantity()))
            .collect(Collectors.toList());

        return OrderCreateDto.craete(this.supplierId, this.receiverId, this.originHubId,
            this.destinationHubId, this.recipientName, this.recipientSlackId, productDtos);
    }


}
