package com.namequickly.logistics.order.application.dto.client;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HubResponseDto {

    private UUID hubId;
    private String hubName;
    private String address;
    private String locationLatitude;
    private String locationLongitude;

}
