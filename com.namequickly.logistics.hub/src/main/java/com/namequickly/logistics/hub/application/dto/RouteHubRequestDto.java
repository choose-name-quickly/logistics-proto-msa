package com.namequickly.logistics.hub.application.dto;

import java.util.UUID;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RouteHubRequestDto {
    private UUID routeHubId;
    private UUID routeId;
    private UUID hubId;
    private UUID nextHubId;
    private Double distanceToNextHub;
    private Double timeToNextHub;
    private Boolean isDelete;
}
