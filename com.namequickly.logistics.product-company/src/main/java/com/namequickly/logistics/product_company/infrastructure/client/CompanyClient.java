package com.namequickly.logistics.product_company.infrastructure.client;

import com.namequickly.logistics.product_company.application.dto.client.CompanyResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "hub-management-service")
public interface CompanyClient {

    @GetMapping(value = "/api/companies/{companyId}")
    CompanyResponse getCompanyById(@PathVariable(name = "companyId") UUID companyId,
        @RequestHeader("X-User-Roles") String role);
}
