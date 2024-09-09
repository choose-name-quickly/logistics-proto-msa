package com.namequickly.logistics.domain.hub.repository;

import com.namequickly.logistics.domain.hub.domain.Hub;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRepository extends JpaRepository<Hub, UUID> {

}
