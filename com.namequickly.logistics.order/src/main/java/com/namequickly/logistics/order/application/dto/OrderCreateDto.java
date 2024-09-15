package com.namequickly.logistics.order.application.dto;

import com.namequickly.logistics.order.presentation.dto.OrderCreateRequestDto.OrderProductRequestDto;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class OrderCreateDto {

    private final UUID supplierId;
    private final UUID receiverId;
    private final UUID originHubId;
    private final UUID destinationHubId;
    private final String recipientName;
    private final String recipientSlackId;
    private final List<OrderProductDto> products;

    @Getter
    @AllArgsConstructor
    public static class OrderProductDto {

        private final UUID productId; // 외부 상품 id
        private final Integer orderQuantity; // 주문 상품

    }


    public static OrderCreateDto craete(UUID supplierId, UUID receiverId, UUID originHubId,
        UUID destinationHubId, String recipientName, String recipientSlackId,
        List<OrderProductDto> products) {
        return OrderCreateDto.builder()
            .supplierId(supplierId)
            .receiverId(receiverId)
            .originHubId(originHubId)
            .destinationHubId(destinationHubId)
            .recipientName(recipientName)
            .recipientSlackId(recipientSlackId)
            .products(Collections.unmodifiableList(products))
            .build();
    }
}
