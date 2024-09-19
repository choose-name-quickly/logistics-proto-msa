package com.namequickly.logistics.hub.domain.repository;

import com.namequickly.logistics.common.shared.affiliation.HubAffiliation;
import com.namequickly.logistics.hub.domain.model.Hub;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRepository extends JpaRepository<Hub, UUID> {

    Optional<Hub> findByHubName(String hubName);
    Optional<Hub> findHubIdByAffiliationId(HubAffiliation affiliationId);
}
