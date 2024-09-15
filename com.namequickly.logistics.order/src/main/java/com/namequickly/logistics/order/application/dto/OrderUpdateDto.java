package com.namequickly.logistics.order.application.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderUpdateDto {

    private UUID productId;
    private Integer orderQuantity;

    public static OrderUpdateDto create(UUID productId, Integer orderQuantity) {
        return OrderUpdateDto.builder()
            .productId(productId)
            .orderQuantity(orderQuantity)
            .build();
    }
}
