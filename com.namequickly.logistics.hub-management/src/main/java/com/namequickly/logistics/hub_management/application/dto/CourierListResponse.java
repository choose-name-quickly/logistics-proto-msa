package com.namequickly.logistics.hub_management.application.dto;

import com.namequickly.logistics.hub_management.domain.model.courier.Courier;
import com.namequickly.logistics.hub_management.domain.model.courier.CourierStatus;
import com.namequickly.logistics.hub_management.domain.model.courier.CourierType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CourierListResponse {
    private CourierType type;
    private UUID hubId;
    private String name;
    private CourierStatus status;


    public static CourierListResponse toResponse(Courier courier) {
        return new CourierListResponse(
                courier.getType(),
                courier.getHubId(),
                courier.getName(),
                courier.getStatus()
        );
    }
}
