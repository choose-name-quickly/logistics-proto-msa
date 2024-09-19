package com.namequickly.logistics.hub.domain.repository;

import com.namequickly.logistics.hub.domain.model.Hub;
import com.namequickly.logistics.hub.domain.model.RouteHub;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteHubRepository extends JpaRepository<RouteHub, UUID> {
    Optional<RouteHub> findByHubIdAndNextHubId(UUID currentHub, UUID nextHub);
    List<RouteHub> findByRouteId(UUID routeId);
}
