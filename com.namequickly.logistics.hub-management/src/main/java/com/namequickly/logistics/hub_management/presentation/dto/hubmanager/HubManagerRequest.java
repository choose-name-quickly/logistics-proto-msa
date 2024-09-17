package com.namequickly.logistics.hub_management.presentation.dto.hubmanager;

import java.util.UUID;

public record HubManagerRequest (
        UUID hubId,
        String name,
        String address,
        String phone
){
}
