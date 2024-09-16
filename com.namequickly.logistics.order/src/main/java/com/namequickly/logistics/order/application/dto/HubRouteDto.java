package com.namequickly.logistics.order.application.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HubRouteDto {

    private final UUID routeHubId;
    private final UUID courierId;
    //private final Integer sequence;


}
