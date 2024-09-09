package com.namequickly.logistics.domain.route.repository;

import com.namequickly.logistics.domain.route.domain.Route;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, UUID> {

}