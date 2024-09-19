package com.namequickly.logistics.hub.domain.model;

import com.namequickly.logistics.common.shared.affiliation.HubAffiliation;
import com.namequickly.logistics.hub.application.dto.HubRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "p_hub")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hub extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID hubId;

    private HubAffiliation affiliationId; // 허브소속 ID
    private String hubName; // 허브이름
    private String address; // 허브주소 
    private String locationLatitude; // 위도
    private String locationLongitude; // 경도
    private Boolean isDelete; // 논리적삭제 여부

    public static Hub createHub(HubRequestDto hubDTO) {
        return Hub.builder()
            .affiliationId(hubDTO.getAffiliationId())
            .hubName(hubDTO.getHubName())
            .address(hubDTO.getAddress())
            .locationLatitude(hubDTO.getLocationLatitude())
            .locationLongitude(hubDTO.getLocationLongitude())
            .isDelete(false)
            .build();
    }

    public void updateHub(HubRequestDto hubDTO) {
        this.hubName = hubDTO.getHubName();
        this.address = hubDTO.getAddress();
        this.affiliationId = hubDTO.getAffiliationId();
        this.locationLatitude = hubDTO.getLocationLatitude();
        this.locationLongitude = hubDTO.getLocationLongitude();
    }
}