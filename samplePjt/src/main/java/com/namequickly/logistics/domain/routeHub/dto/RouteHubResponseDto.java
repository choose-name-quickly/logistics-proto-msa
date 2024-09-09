package com.namequickly.logistics.domain.routeHub.dto;

import com.namequickly.logistics.domain.hub.dto.HubRequestDto;
import com.namequickly.logistics.domain.route.dto.RouteRequestDto;
import lombok.*;
import java.util.UUID;

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
