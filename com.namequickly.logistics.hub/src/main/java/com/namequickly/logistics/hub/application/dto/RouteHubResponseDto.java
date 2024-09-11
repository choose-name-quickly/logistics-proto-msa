package com.namequickly.logistics.hub.application.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RouteHubResponseDto {

    private UUID routeHubId;
    private RouteRequestDto route;
    private HubRequestDto hub;
    private int orderInRoute;
    private double distanceToNextHub;
    private double timeToNextHub;
}