package com.namequickly.logistics.order.application.dto.client;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HubRouteDto {

    private UUID routeHubId;
    private UUID routeId;
    private UUID hubId;
    private Integer orderInRoute;
    private BigDecimal distanceToNextHub;
    private BigDecimal timeToNextHub;
}
