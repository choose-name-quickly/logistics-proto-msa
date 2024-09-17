package com.namequickly.logistics.order.application.dto.client;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HubRouteCourierDto {

    private final UUID routeHubId;
    private final UUID courierId;
}
