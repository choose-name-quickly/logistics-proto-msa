package com.namequickly.logistics.order.infrastructure.repository;

import com.namequickly.logistics.order.domain.model.delivery.DeliveryRoute;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface DeliveryRouteRepository extends JpaRepository<DeliveryRoute, UUID> {

    List<DeliveryRoute> findByDelivery_DeliveryId(@Param("deliveryId") UUID deliveryId);

}
