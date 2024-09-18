package com.namequickly.logistics.hub.application.dto;

import com.namequickly.logistics.common.shared.affiliation.HubAffiliation;
import java.util.UUID;
import lombok.Getter;

@Getter
public class HubResponseDto {

    private UUID hubId;
    private HubAffiliation affiliationId;
    private String hubName;
    private String address;
    private String locationLatitude;
    private String locationLongitude;
}
