package com.namequickly.logistics.hub_management.presentation.dto.hubmanager;

import java.util.UUID;

public record HubManagerSearch(
        UUID managerId,
        UUID hubId,
        String name
) {
}
