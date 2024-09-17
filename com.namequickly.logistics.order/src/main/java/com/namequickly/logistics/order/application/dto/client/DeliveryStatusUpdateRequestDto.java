package com.namequickly.logistics.order.application.dto.client;

import com.namequickly.logistics.order.domain.model.delivery.DeliveryStatus;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryStatusUpdateRequestDto {

    private UUID deliveryRouteId;
    private UUID courierId;
    private DeliveryStatus deliveryStatus;
    private BigDecimal actualDistance;

}
