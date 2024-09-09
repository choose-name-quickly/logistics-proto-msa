package com.namequickly.logistics.domain.route.dto;

import lombok.*;
import java.util.UUID;

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
