package com.namequickly.logistics.order.infrastructure.repository;

import com.namequickly.logistics.order.domain.model.order.Order;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT o FROM Order o " +
        "JOIN o.orderProducts op " +
        "JOIN o.delivery d " +
        "JOIN d.deliveryRoutes dr " +
        "WHERE o.orderId = :orderId AND o.isDelete = :isDelete")
    Optional<Order> findOrderDetails(@Param("orderId") UUID orderId,
        @Param("isDelete") boolean isDelete);

    @Query(value = "SELECT o FROM Order o " +
        "JOIN o.orderProducts op " +
        "JOIN o.delivery d " +
        "JOIN d.deliveryRoutes dr " +
        "WHERE o.isDelete = :isDelete",
        countQuery = "SELECT COUNT(DISTINCT o.orderId) FROM Order o WHERE o.isDelete = :isDelete")
    Page<Order> findAllOrderDetails(Pageable pageable, @Param("isDelete") boolean isDelete);

    @Query(value = "SELECT DISTINCT o FROM Order o " +
        "JOIN o.orderProducts op " +
        "JOIN o.delivery d " +
        "JOIN d.deliveryRoutes dr " +
        "WHERE o.isDelete = :isDelete AND o.supplierId =:affiliationId",
        countQuery = "SELECT COUNT(DISTINCT o.orderId) FROM Order o WHERE o.isDelete = :isDelete and o.supplierId = :affiliationId")
    Page<Order> findAllOrderDetailsByHubId(Pageable pageable, @Param("isDelete") boolean isDelete,
        @Param("affiliationId") UUID affiliationId);

    @Query(value = "SELECT DISTINCT o FROM Order o " +
        "JOIN o.orderProducts op " +
        "JOIN o.delivery d " +
        "JOIN d.deliveryRoutes dr " +
        "WHERE o.isDelete = :isDelete AND d.originHubId =:affiliationId",
        countQuery = "SELECT COUNT(DISTINCT o.orderId) FROM Order o LEFT JOIN FETCH Delivery d " +
            "WHERE d.originHubId = :affiliationId AND o.isDelete = :isDelete")
    Page<Order> findAllOrderDetailsByCompanyId(Pageable pageable,
        @Param("isDelete") boolean isDelete,
        @Param("affiliationId") UUID affiliationId);
}

