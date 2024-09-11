package com.namequickly.logistics.hub.application.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteResponseDto {

    private UUID routeId;
    private HubResponseDto originHub;
    private HubResponseDto destinationHub;
    private String routeDescription;
    private double estimatedTime;
    private String distance;
}
