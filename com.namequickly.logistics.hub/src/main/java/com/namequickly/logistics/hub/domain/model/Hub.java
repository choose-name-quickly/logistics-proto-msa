package com.namequickly.logistics.hub.domain.model;

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

@Entity
@Table(name = "p_hub")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hub extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID hubId;

    private String hubName;
    private String address;
    private String locationLatitude;
    private String locationLongitude;

    public static Hub createHub(HubRequestDto hubDTO) {
        return Hub.builder()
            .hubName(hubDTO.getHubName())
            .address(hubDTO.getAddress())
            .locationLatitude(hubDTO.getLocationLatitude())
            .locationLongitude(hubDTO.getLocationLongitude())
            .build();
    }

    public void updateHub(HubRequestDto hubDTO) {
        this.hubName = hubDTO.getHubName();
        this.address = hubDTO.getAddress();
        this.locationLatitude = hubDTO.getLocationLatitude();
        this.locationLongitude = hubDTO.getLocationLongitude();
    }
}