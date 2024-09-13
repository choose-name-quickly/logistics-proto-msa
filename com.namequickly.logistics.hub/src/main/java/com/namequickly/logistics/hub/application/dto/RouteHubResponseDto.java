package com.namequickly.logistics.hub.application.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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