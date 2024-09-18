package com.namequickly.logistics.order.infrastructure.repository;

import com.namequickly.logistics.order.domain.model.delivery.Delivery;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {

    @Query("select d from Delivery d LEFT JOIN FETCH d.deliveryRoutes WHERE d.deliveryId = :deliveryId AND d.isDelete = false")
    Optional<Delivery> findByDeliveryIdAndIsDeleteFalse(@Param("deliveryId") UUID deliveryId);

    long countByDeliveryId(UUID deliveryId);
}
