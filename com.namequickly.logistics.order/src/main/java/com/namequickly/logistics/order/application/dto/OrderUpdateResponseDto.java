package com.namequickly.logistics.order.application.dto;

import java.time.LocalDateTime;
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
public class OrderUpdateResponseDto {

    private UUID orderId;
    private List<OrderProductResponseDto> products;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OrderProductResponseDto {

        private UUID productId;
        private Integer orderQuantity;
        private LocalDateTime updatedBy;
    }

}
