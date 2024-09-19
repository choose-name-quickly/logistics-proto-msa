package com.namequickly.logistics.product_company.domain.repository;

import com.namequickly.logistics.product_company.domain.model.Product;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @EntityGraph(attributePaths = {"stock"})
    Optional<Product> findById(UUID productId);

    @EntityGraph(attributePaths = {"stock"})
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.stock WHERE p.productId = :productId AND p.isDelete = false")
    Optional<Product> findByProductIdAndIsDeleteFalse(@Param("productId") UUID productId);

    boolean existsByProductName(@Param("productName") String productName);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.stock WHERE p.productName = :productName AND p.isDelete = false")
    Optional<Product> findByProductNameAndIsDeleteFalse(@Param("productName") String productName);

    @Query(value = "select p from Product p where p.isDelete = :isDelete",
        countQuery = "select COUNT(p.productId) from Product p where p.isDelete = :isDelete")
    Page<Product> findAllProducts(Pageable pageable, @Param("isDelete") boolean isDelete);

    @Query(value = "select p from Product p where p.isDelete = :isDelete and p.hubId = :hubId",
        countQuery = "select COUNT(p.productId) from Product p where p.isDelete = :isDelete and p.hubId = :hubId")
    Page<Product> findAllProductsByHubId(Pageable pageable, @Param("hubId") UUID hubId,
        @Param("isDelete") boolean isDelete);

    @Query(value = "select p from Product p where p.isDelete = :isDelete and p.supplierId = :supplierId",
        countQuery = "select COUNT(p) from Product p where p.isDelete = :isDelete and p.supplierId = :supplierId")
    Page<Product> findAllProductsBySupplierId(Pageable pageable,
        @Param("supplierId") UUID supplierId, @Param("isDelete") boolean isDelete);
}
