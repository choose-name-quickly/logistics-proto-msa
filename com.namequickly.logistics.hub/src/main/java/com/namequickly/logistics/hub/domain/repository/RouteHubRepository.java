package com.namequickly.logistics.hub.domain.repository;

import com.namequickly.logistics.hub.domain.model.RouteHub;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteHubRepository extends JpaRepository<RouteHub, UUID> {

}
