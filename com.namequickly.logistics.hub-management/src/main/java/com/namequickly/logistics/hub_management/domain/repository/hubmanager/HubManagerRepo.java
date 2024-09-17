package com.namequickly.logistics.hub_management.domain.repository.hubmanager;

import com.namequickly.logistics.hub_management.domain.model.hubmanager.HubManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HubManagerRepo extends JpaRepository<HubManager, UUID>, HubManagerRepoCustom {

    @Query("SELECT h.managerId FROM HubManager h")
    List<UUID> findAllHubManagerIds();
}
