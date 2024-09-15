package com.namequickly.logistics.order.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderResponseDto {

    private final UUID orderId;
    private final UUID supplierId;
    private final UUID receiverId;
    private final List<OrderProductDto> orderProducts;
    private final DeliveryDto delivery;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    @Getter(AccessLevel.NONE)
    private final boolean isDelete;

    public Boolean getIsDelete() {
        return isDelete;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class OrderProductDto {

        private final UUID productId;
        private final Integer orderQuantity;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;
        @Getter(AccessLevel.NONE)
        private final boolean isDelete;

        public Boolean getIsDelete() {
            return isDelete;
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class DeliveryDto {

        private final UUID deliveryId;
        private final UUID originHubId;
        private final UUID destinationHubId;
        private final String recipientName;
        private final String recipientSlackId;
        private final List<DeliveryRouteDto> deliveryRoutes;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;
        @Getter(AccessLevel.NONE)
        private final boolean isDelete;

        public Boolean getIsDelete() {
            return isDelete;
        }

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class DeliveryRouteDto {

        private final UUID routeHubId;
        private final UUID courierId;
        private final Integer sequence;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;
        @Getter(AccessLevel.NONE)
        private final boolean isDelete;

        public Boolean getIsDelete() {
            return isDelete;
        }
    }
}
