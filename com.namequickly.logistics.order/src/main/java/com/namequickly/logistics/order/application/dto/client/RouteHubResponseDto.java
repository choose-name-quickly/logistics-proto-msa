package com.namequickly.logistics.order.application.dto.client;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RouteHubResponseDto {

    private UUID routeHubId;
    private RouteRequestDto route;
    private HubRequestDto hub;
    private int orderInRoute;
    private double distanceToNextHub;
    private double timeToNextHub;
}