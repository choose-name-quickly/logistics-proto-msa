package com.namequickly.logistics.hub_management.domain.repository.company;

import com.namequickly.logistics.hub_management.application.dto.CompanyResponse;
import com.namequickly.logistics.hub_management.presentation.dto.company.CompanySearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyRepoCustom {

    Page<CompanyResponse> searchCompanies(CompanySearch search, Pageable pageable);
}
