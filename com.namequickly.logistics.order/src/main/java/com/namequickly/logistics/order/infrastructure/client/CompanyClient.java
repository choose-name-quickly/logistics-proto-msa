package com.namequickly.logistics.order.infrastructure.client;

import com.namequickly.logistics.order.application.dto.client.CompanyResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "hub-management-service")
public interface CompanyClient {

    @GetMapping("/api/companies/{companyId}")
    CompanyResponse getCompanyById(@PathVariable(name = "companyId") UUID companyId,
        @RequestHeader(value = "X-User-Roles") String role);
}
