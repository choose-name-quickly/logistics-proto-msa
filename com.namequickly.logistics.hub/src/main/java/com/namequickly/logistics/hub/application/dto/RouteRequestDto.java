package com.namequickly.logistics.hub.application.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteRequestDto {

    private UUID routeId;
    private UUID originHubId;
    private UUID destinationHubId;
    private String routeDescription;
    private Double estimatedTime;
    private String distance;
    private Boolean isDelete;
}