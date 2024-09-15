package com.namequickly.logistics.product_company.infrastructure.client;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hub-management-service")
public interface CompanyClient {

    // 해당 company 존재 여부 확인
    @GetMapping("/api/hub-management/companies")
    boolean checkCompanyExists(@RequestParam(name = "supplierId") UUID supplierId);
}
