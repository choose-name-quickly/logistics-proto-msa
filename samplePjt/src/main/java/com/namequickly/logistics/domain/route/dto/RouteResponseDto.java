package com.namequickly.logistics.domain.route.dto;

import com.namequickly.logistics.domain.hub.dto.HubResponseDto;
import lombok.*;
import java.util.UUID;

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
