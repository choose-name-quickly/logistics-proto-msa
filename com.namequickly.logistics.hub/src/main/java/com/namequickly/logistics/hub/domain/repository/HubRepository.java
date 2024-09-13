package com.namequickly.logistics.hub.domain.repository;

import com.namequickly.logistics.hub.domain.model.Hub;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRepository extends JpaRepository<Hub, UUID> {

}
