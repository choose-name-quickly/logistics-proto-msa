package com.namequickly.logistics.domain.routeHub.dto;

import java.util.UUID;
import lombok.Getter;

@Getter
public class RouteHubRequestDto {
    private UUID routeHubId;
    private UUID routeId;
    private UUID hubId;
    private Integer orderInRoute;
    private Double distanceToNextHub;
    private Double timeToNextHub;
    private Boolean isDelete;
}
