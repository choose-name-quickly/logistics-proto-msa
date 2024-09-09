package com.namequickly.logistics.domain.routeHub.repository;


import com.namequickly.logistics.domain.routeHub.domain.RouteHub;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteHubRepository extends JpaRepository<RouteHub, UUID> {

}
