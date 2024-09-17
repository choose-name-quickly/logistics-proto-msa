package com.namequickly.logistics.order.infrastructure.repository;

import com.namequickly.logistics.order.domain.model.order.OrderProduct;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, UUID> {

    @EntityGraph(attributePaths = {"order"})
    List<OrderProduct> findAllByproductId(UUID productId);
}
