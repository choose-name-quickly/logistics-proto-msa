package com.namequickly.logistics.product_company.infrastructure.client;

import com.namequickly.logistics.product_company.application.dto.client.CompanyDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hub-management-service")
public interface CompanyClient {

    @GetMapping("/api/hub-management/companies/{company_id}")
    CompanyDto getCompany(@PathVariable(name = "company_id") UUID companyId);
}
