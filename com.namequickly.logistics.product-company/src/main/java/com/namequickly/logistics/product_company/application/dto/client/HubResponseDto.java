package com.namequickly.logistics.product_company.application.dto.client;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HubResponseDto {

    private UUID hubId;
    private String hubName;
    private String address;
    private String locationLatitude;
    private String locationLongitude;
}
