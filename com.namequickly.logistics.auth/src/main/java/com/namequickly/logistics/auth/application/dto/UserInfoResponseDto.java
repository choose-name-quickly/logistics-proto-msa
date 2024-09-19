package com.namequickly.logistics.auth.application.dto;

import com.namequickly.logistics.common.querydsl.QuerydslConfig;
import com.namequickly.logistics.common.response.CommonResponse;
import com.namequickly.logistics.common.shared.UserRole;
import com.namequickly.logistics.common.shared.affiliation.AffiliationType;
import com.namequickly.logistics.common.shared.affiliation.CompanyAffiliation;
import com.namequickly.logistics.common.shared.affiliation.CourierAffiliation;
import com.namequickly.logistics.common.shared.affiliation.HubAffiliation;
import java.util.UUID;

public record UserInfoResponseDto(
    String username,
    UserRole role,
    AffiliationType affiliationType,
    UUID companyAffiliationId,
    UUID courierAffiliationId,
    UUID hubAffiliationId
) {

}
