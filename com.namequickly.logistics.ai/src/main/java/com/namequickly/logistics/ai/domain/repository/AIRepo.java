package com.namequickly.logistics.ai.domain.repository;

import com.namequickly.logistics.ai.domain.model.AI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AIRepo extends JpaRepository<AI, UUID>, AIRepoCustom {
}
