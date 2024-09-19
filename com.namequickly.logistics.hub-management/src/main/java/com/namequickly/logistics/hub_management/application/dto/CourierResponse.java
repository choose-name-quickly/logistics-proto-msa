package com.namequickly.logistics.hub_management.application.dto;

import com.namequickly.logistics.hub_management.domain.model.courier.Courier;
import com.namequickly.logistics.hub_management.domain.model.courier.CourierStatus;
import com.namequickly.logistics.hub_management.domain.model.courier.CourierType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CourierResponse {
    private UUID courierId;
    private CourierType type;
    private UUID hubId;
    private String name;
    private String address;
    private String phone;
    private CourierStatus status;
    private Timestamp createdAt;
    private String createdBy;
    private Timestamp updatedAt;
    private String updatedBy;
    private Timestamp deletedAt;
    private String deletedBy;
    private Boolean isDelete;

    public static CourierResponse toResponse(Courier courier) {
        return new CourierResponse(
                courier.getCourierId(),
                courier.getType(),
                courier.getHubId(),
                courier.getName(),
                courier.getAddress(),
                courier.getPhone(),
                courier.getStatus(),
                courier.getCreatedAt(),
                courier.getCreatedBy(),
                courier.getUpdatedAt(),
                courier.getUpdatedBy(),
                courier.getDeletedAt(),
                courier.getDeletedBy(),
                courier.getIsDelete()
        );
    }
}
