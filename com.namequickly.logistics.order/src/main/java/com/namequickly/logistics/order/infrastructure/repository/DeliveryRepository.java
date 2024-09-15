package com.namequickly.logistics.order.infrastructure.repository;

import com.namequickly.logistics.order.domain.model.delivery.Delivery;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {

    Delivery findByOrder_OrderId(@Param("orderId") UUID orderId);
}
