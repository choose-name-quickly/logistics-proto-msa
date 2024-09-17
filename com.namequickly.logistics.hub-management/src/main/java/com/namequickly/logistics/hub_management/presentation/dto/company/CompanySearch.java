package com.namequickly.logistics.hub_management.presentation.dto.company;

import java.util.UUID;

public record CompanySearch(
        UUID companyId,
        UUID hubId,
        String name
) {
}
