package com.namequickly.logistics.hub_management.application.dto;

import com.namequickly.logistics.hub_management.domain.model.company.Company;
import com.namequickly.logistics.hub_management.domain.model.company.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyListResponse {
    private CompanyType type;
    private UUID hubId;
    private String name;

    public static CompanyListResponse toResponse(Company company) {
        return new CompanyListResponse(
                company.getType(),
                company.getHubId(),
                company.getName()
        );
    }
}
