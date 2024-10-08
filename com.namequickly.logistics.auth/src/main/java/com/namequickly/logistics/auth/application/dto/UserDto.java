package com.namequickly.logistics.auth.application.dto;

import com.namequickly.logistics.common.shared.UserRole;
import com.namequickly.logistics.common.shared.affiliation.AffiliationType;
import com.namequickly.logistics.common.shared.affiliation.CompanyAffiliation;
import com.namequickly.logistics.common.shared.affiliation.CourierAffiliation;
import com.namequickly.logistics.common.shared.affiliation.HubAffiliation;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// LocalDateTime 을 제거한 redis 캐싱용 Dto

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private String username;
    private String password;
    private UserRole role;
    private AffiliationType affiliationType;
    private UUID companyAffiliationId;
    private UUID courierAffiliationId;
    private UUID hubAffiliationId;
}
