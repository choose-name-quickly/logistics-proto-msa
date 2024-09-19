package com.namequickly.logistics.hub_management.domain.repository.company;

import com.namequickly.logistics.hub_management.domain.model.company.Company;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CompanyRepo extends JpaRepository<Company, UUID>, CompanyRepoCustom {

    @Query("SELECT c.companyId FROM Company c WHERE c.companyId = :companyId")
    UUID checkId(@Param("companyId") UUID companyId);

    @Query("SELECT c.hubId FROM Company c WHERE c.companyId = :companyId")
    UUID findHubId(@Param("companyId") UUID companyId);
}
