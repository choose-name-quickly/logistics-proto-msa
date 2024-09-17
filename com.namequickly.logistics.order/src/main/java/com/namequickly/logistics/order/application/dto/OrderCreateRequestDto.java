package com.namequickly.logistics.order.application.dto;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreateRequestDto {

    // 주문
    private UUID supplierId;
    private UUID receiverId;

    // 주문 상품
    private List<OrderProductRequestDto> products;

    // 배송
    private UUID originHubId;
    private UUID destinationHubId;
    private String recipientName;
    private String recipientSlackId;
    private String deliveryAddress;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OrderProductRequestDto {

        private UUID productId; // 외부 상품 id
        private Integer orderQuantity; // 주문 상품
    }


}
