package com.namequickly.logistics.hub_management.domain.repository.courier;

import com.namequickly.logistics.hub_management.domain.model.courier.Courier;
import com.namequickly.logistics.hub_management.domain.model.courier.CourierStatus;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourierRepo extends JpaRepository<Courier, UUID>, CourierRepoCustom {

    @Query("SELECT c.courierId FROM Courier c WHERE c.status = :status")
    List<UUID> findCourierIdsByStatus(@Param("status") CourierStatus status);

    @Query("SELECT c.courierId FROM Courier c")
    List<UUID> findAllCourierIds();
}
