package com.namequickly.logistics.hub_management.presentation.dto.courier;

import com.namequickly.logistics.hub_management.domain.model.courier.CourierStatus;

import java.util.UUID;

public record CourierSearch(
        UUID courierId,
        UUID hubId,
        String name,
        CourierStatus status
) {
}
