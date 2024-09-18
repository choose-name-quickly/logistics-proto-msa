package com.namequickly.logistics.order.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private UUID orderId;
    private UUID supplierId;
    private UUID receiverId;
    private List<OrderProductDto> orderProducts;
    private DeliveryDto delivery;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Getter(AccessLevel.NONE)
    private boolean isDelete;

    public Boolean getIsDelete() {
        return isDelete;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderProductDto {

        private UUID productId;
        private Integer orderQuantity;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        @Getter(AccessLevel.NONE)
        private boolean isDelete;

        public Boolean getIsDelete() {
            return isDelete;
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeliveryDto {

        private UUID deliveryId;
        private UUID originHubId;
        private UUID destinationHubId;
        private String recipientName;
        private String recipientSlackId;
        private String deliveryAddress;
        private List<DeliveryRouteDto> deliveryRoutes;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        @Getter(AccessLevel.NONE)
        private boolean isDelete;

        public Boolean getIsDelete() {
            return isDelete;
        }

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeliveryRouteDto {

        private UUID routeHubId;
        private UUID courierId;
        private Integer sequence;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        @Getter(AccessLevel.NONE)
        private boolean isDelete;

        public Boolean getIsDelete() {
            return isDelete;
        }
    }
}
