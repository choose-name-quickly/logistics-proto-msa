package com.namequickly.logistics.order.infrastructure.repository;

import com.namequickly.logistics.order.domain.model.delivery.DeliveryRoute;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeliveryRouteRepository extends JpaRepository<DeliveryRoute, UUID> {

    List<DeliveryRoute> findByDelivery_DeliveryId(@Param("deliveryId") UUID deliveryId);

    @Query("select dr from DeliveryRoute dr left join fetch dr.delivery d where dr.courierId = :courierId and dr.isDelete = false")
    List<DeliveryRoute> findByCourierId(UUID courierId);

    @Query("select dr from DeliveryRoute dr left join fetch dr.delivery d where dr.deliveryRouteId = :deliveryRouteId and dr.isDelete = false")
    Optional<DeliveryRoute> findByDeliveryRouteId(UUID deliveryRouteId);
}
