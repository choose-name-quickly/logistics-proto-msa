package com.namequickly.logistics.order.application.dto.client;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class RouteHubResponseDto {

    private UUID routeHubId;
    private UUID hubId; // 허브 ID
    private UUID nextHubId; // 다음허브 ID
    private int orderInRoute;
    private double distanceToNextHub;
    private double timeToNextHub;
}
