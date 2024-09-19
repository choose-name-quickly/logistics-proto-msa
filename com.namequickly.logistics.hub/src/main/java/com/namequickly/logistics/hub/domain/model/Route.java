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

    private UUID originHubId; // 출발 허브 ID
    private UUID destinationHubId; // 도착 허브 ID
    private String routeDescription; // 경로 설명
    private Double estimatedTime; // 예상 소요 시간
    private String distance; // 경로 거리
    private Boolean isDelete; // 논리적 삭제 여부

    public static Route createRoute(RouteRequestDto routeDTO) {
        return Route.builder()
            .originHubId(routeDTO.getOriginHubId())
            .destinationHubId(routeDTO.getDestinationHubId())
            .routeDescription(routeDTO.getRouteDescription())
            .estimatedTime(routeDTO.getEstimatedTime())
            .distance(routeDTO.getDistance())
            .isDelete(false)
            .build();
    }

    public void updateRoute(RouteRequestDto routeDTO) {
        this.originHubId = routeDTO.getOriginHubId();
        this.destinationHubId = routeDTO.getDestinationHubId();
        this.routeDescription = routeDTO.getRouteDescription();
        this.estimatedTime = routeDTO.getEstimatedTime();
        this.distance = routeDTO.getDistance();
    }
}
