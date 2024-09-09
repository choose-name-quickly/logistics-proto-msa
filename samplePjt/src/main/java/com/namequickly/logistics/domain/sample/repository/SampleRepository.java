package com.namequickly.logistics.domain.sample.repository;

import com.namequickly.logistics.domain.sample.domain.Sample;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<Sample, UUID> {

}
