package com.namequickly.logistics.hub_management.presentation.dto.company;

import com.namequickly.logistics.hub_management.domain.model.company.CompanyType;

import java.util.UUID;

public record CompanyRequest(
        CompanyType type,
        UUID hubId,
        String name,
        String address,
        String phone
) {
}
