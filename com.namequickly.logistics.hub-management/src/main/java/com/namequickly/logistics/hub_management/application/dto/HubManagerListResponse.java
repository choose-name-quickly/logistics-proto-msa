package com.namequickly.logistics.hub_management.application.dto;

import com.namequickly.logistics.hub_management.domain.model.hubmanager.HubManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HubManagerListResponse {

    private UUID hubId;
    private String name;

    public static HubManagerListResponse toResponse(HubManager hubManager) {
        return new HubManagerListResponse(
                hubManager.getHubId(),
                hubManager.getName()
        );
    }
}
