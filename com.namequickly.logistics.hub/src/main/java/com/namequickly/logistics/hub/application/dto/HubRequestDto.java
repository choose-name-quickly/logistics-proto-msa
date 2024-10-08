package com.namequickly.logistics.hub.application.dto;

import com.namequickly.logistics.common.shared.affiliation.HubAffiliation;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class HubRequestDto {

    private UUID hubId;
    private HubAffiliation affiliationId;
    private String hubName;
    private String address;
    private String locationLatitude;
    private String locationLongitude;
    private Boolean isDelete;

}
