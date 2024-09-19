package com.namequickly.logistics.hub.domain.model;

import com.namequickly.logistics.hub.application.dto.RouteHubRequestDto;
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
@Table(name = "p_route_hub")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RouteHub extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID routeHubId;

    private UUID routeId; // 경로 ID
    private UUID hubId; // 허브 ID
    private UUID nextHubId; // 다음허브 ID
    private int orderInRoute; // 경로산 순서
    private Double distanceToNextHub; // 다음 허브까지 거리
    private Double timeToNextHub; // 다음 허브까지 소요 시간
    private Boolean isDelete; // 논리적 삭제 여부

    public static RouteHub createRouteHub(RouteHubRequestDto routeHubDTO) {
        return RouteHub.builder()
            .routeId(routeHubDTO.getRouteId())
            .hubId(routeHubDTO.getHubId())
            .nextHubId(routeHubDTO.getNextHubId())
            .distanceToNextHub(routeHubDTO.getDistanceToNextHub())
            .timeToNextHub(routeHubDTO.getTimeToNextHub())
            .isDelete(false)
            .build();
    }

    public void updateRouteHub(RouteHubRequestDto routeHubDTO) {
        this.routeId = routeHubDTO.getRouteId();
        this.hubId = routeHubDTO.getHubId();
        this.nextHubId = routeHubDTO.getNextHubId();
        this.distanceToNextHub = routeHubDTO.getDistanceToNextHub();
        this.timeToNextHub = routeHubDTO.getTimeToNextHub();
    }
}