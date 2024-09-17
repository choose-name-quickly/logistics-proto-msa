package com.namequickly.logistics.order.application.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DeliveryRouteDto {

    private final UUID deliveryRouteId;
    private final UUID routeHubId;
    private final UUID courierId;
    private final Integer sequence;
    private final boolean isDelete;
}
