package com.namequickly.logistics.hub.domain.model;

import com.namequickly.logistics.hub.application.dto.RouteRequestDto;
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
@Table(name = "p_route")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Route extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID routeId;

    private UUID originHubId;
    private UUID destinationHubId;
    private String routeDescription;
    private Double estimatedTime;
    private String distance;
    private Boolean isDelete;

    public static Route createRoute(RouteRequestDto routeDTO) {
        return Route.builder()
            .originHubId(routeDTO.getOriginHubId())
            .destinationHubId(routeDTO.getDestinationHubId())
            .routeDescription(routeDTO.getRouteDescription())
            .estimatedTime(routeDTO.getEstimatedTime())
            .distance(routeDTO.getDistance())
            .isDelete(routeDTO.getIsDelete())
            .build();
    }

    public void updateRoute(RouteRequestDto routeDTO) {
        this.originHubId = routeDTO.getOriginHubId();
        this.destinationHubId = routeDTO.getDestinationHubId();
        this.routeDescription = routeDTO.getRouteDescription();
        this.estimatedTime = routeDTO.getEstimatedTime();
        this.distance = routeDTO.getDistance();
        this.isDelete = routeDTO.getIsDelete();
    }
}
