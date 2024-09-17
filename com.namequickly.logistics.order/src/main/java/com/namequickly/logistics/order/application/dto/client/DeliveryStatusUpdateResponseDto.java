package com.namequickly.logistics.order.application.dto.client;

import com.namequickly.logistics.order.domain.model.delivery.DeliveryStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryStatusUpdateResponseDto {

    private UUID deliveryRouteId;
    private UUID courierId;
    private DeliveryStatus beforeDeliveryStatus;
    private DeliveryStatus afterDeliveryStatus;
    private LocalDateTime updatedAt;

}
