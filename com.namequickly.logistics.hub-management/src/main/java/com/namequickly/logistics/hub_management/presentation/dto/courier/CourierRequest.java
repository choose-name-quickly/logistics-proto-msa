package com.namequickly.logistics.hub_management.presentation.dto.courier;

import com.namequickly.logistics.hub_management.domain.model.courier.CourierStatus;
import com.namequickly.logistics.hub_management.domain.model.courier.CourierType;

import java.util.UUID;

public record CourierRequest(
        CourierType type,
        UUID hubId,
        String name,
        String address,
        String phone,
        CourierStatus status
) {
}
