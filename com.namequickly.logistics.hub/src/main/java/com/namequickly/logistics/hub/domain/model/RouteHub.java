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

@Entity
@Table(name = "p_route_hub")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteHub extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID routeHubId;

    private UUID routeId;
    private UUID hubId;
    private Integer orderInRoute;
    private Double distanceToNextHub;
    private Double timeToNextHub;
    private Boolean isDelete;

    public static RouteHub createRouteHub(RouteHubRequestDto routeHubDTO) {
        return RouteHub.builder()
            .routeId(routeHubDTO.getRouteId())
            .hubId(routeHubDTO.getHubId())
            .orderInRoute(routeHubDTO.getOrderInRoute())
            .distanceToNextHub(routeHubDTO.getDistanceToNextHub())
            .timeToNextHub(routeHubDTO.getTimeToNextHub())
            .isDelete(routeHubDTO.getIsDelete())
            .build();
    }

    public void updateRouteHub(RouteHubRequestDto routeHubDTO) {
        this.routeId = routeHubDTO.getRouteId();
        this.hubId = routeHubDTO.getHubId();
        this.orderInRoute = routeHubDTO.getOrderInRoute();
        this.distanceToNextHub = routeHubDTO.getDistanceToNextHub();
        this.timeToNextHub = routeHubDTO.getTimeToNextHub();
        this.isDelete = routeHubDTO.getIsDelete();
    }
}