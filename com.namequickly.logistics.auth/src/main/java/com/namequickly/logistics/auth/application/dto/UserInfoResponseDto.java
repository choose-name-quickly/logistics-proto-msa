package com.namequickly.logistics.auth.application.dto;

import com.namequickly.logistics.common.querydsl.QuerydslConfig;
import com.namequickly.logistics.common.response.CommonResponse;
import com.namequickly.logistics.common.shared.UserRole;
import com.namequickly.logistics.common.shared.affiliation.AffiliationType;
import com.namequickly.logistics.common.shared.affiliation.CompanyAffiliation;
import com.namequickly.logistics.common.shared.affiliation.CourierAffiliation;
import com.namequickly.logistics.common.shared.affiliation.HubAffiliation;

public record UserInfoResponseDto(
    String username,
    UserRole role,
    AffiliationType affiliationType,
    CompanyAffiliation companyAffiliationId,
    CourierAffiliation courierAffiliationId,
    HubAffiliation hubAffiliationId
) {

}
