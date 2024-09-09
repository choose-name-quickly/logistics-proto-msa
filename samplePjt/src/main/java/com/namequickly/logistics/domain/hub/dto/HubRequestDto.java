package com.namequickly.logistics.domain.hub.dto;

import java.util.UUID;
import lombok.Getter;

@Getter
public class HubRequestDto {

    private UUID hubId;
    private String hubName;
    private String address;
    private String locationLatitude;
    private String locationLongitude;
    private Boolean isDelete;
}
