package com.namequickly.logistics.hub_management.application.dto;

import com.namequickly.logistics.hub_management.domain.model.hubmanager.HubManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HubManagerResponse {
    private UUID managerId;
    private UUID hubId;
    private String name;
    private String address;
    private String phone;
    private Timestamp createdAt;
    private UUID createdBy;
    private Timestamp updatedAt;
    private UUID updatedBy;
    private Timestamp deletedAt;
    private UUID deletedBy;
    private Boolean isDelete;

    public static HubManagerResponse toResponse(HubManager hubManager) {
        return new HubManagerResponse(
                hubManager.getManagerId(),
                hubManager.getHubId(),
                hubManager.getName(),
                hubManager.getAddress(),
                hubManager.getPhone(),
                hubManager.getCreatedAt(),
                hubManager.getCreatedBy(),
                hubManager.getUpdatedAt(),
                hubManager.getUpdatedBy(),
                hubManager.getDeletedAt(),
                hubManager.getDeletedBy(),
                hubManager.getIsDelete()
        );
    }
}
