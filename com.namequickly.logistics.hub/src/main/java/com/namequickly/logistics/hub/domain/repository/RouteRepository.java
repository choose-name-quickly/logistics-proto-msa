package com.namequickly.logistics.hub.domain.repository;

import com.namequickly.logistics.hub.domain.model.Route;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, UUID> {
    List<Route> findByOriginHubIdAndDestinationHubId(UUID originHubId, UUID destinationHubId);
}