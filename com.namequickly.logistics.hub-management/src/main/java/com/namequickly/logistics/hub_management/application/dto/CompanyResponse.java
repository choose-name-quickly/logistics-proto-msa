package com.namequickly.logistics.hub_management.application.dto;

import com.namequickly.logistics.hub_management.domain.model.company.Company;
import com.namequickly.logistics.hub_management.domain.model.company.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponse {
    private UUID companyId;
    private CompanyType type;
    private UUID hubId;
    private String name;
    private String address;
    private String phone;
    private Timestamp createdAt;
    private String createdBy;
    private Timestamp updatedAt;
    private String updatedBy;
    private Timestamp deletedAt;
    private String deletedBy;
    private Boolean isDelete;

    // 변환 메서드
    public static CompanyResponse toResponse(Company company) {
        return new CompanyResponse(
                company.getCompanyId(),
                company.getType(),
                company.getHubId(),
                company.getName(),
                company.getAddress(),
                company.getPhone(),
                company.getCreatedAt(),
                company.getCreatedBy(),
                company.getUpdatedAt(),
                company.getUpdatedBy(),
                company.getDeletedAt(),
                company.getDeletedBy(),
                company.getIsDelete()
        );
    }
}
