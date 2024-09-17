package com.namequickly.logistics.order.application.dto;


import com.namequickly.logistics.order.domain.model.delivery.DeliveryStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class DeliveryGetResponseDto {

    private UUID deliveryId;
    private UUID orderId;
    private UUID originHubId;
    private UUID destinationHubId;
    private String recipientName;
    private String recipientSlackID;
    private DeliveryStatus deliveryStatus;
    private LocalDateTime createdAt;
    // private LocalDateTime updatedAt;
    private List<DeliveryRouteDto> deliveryRoutes;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class DeliveryRouteDto {

        private UUID deliveryRouteId;
        private UUID routeHubId;
        private UUID courierId;

        /*
            // TODO feign client로 가져와아햐나?
            경유 정보
            hub_id
            //sequence
            distanceToNextHub //예상거리
            timeToNextHub //예상시간

            // 허브 정보
            hub_name
            address

         */
        // private BigDecimal actualDistance;
        // private BigDecimal actualTime;
        private DeliveryStatus deliveryStatus;

    }
}
