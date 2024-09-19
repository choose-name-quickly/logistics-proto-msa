package com.namequickly.logistics.auth.application.dto;

import com.namequickly.logistics.common.exception.GlobalException;
import com.namequickly.logistics.common.response.ResultCase;
import com.namequickly.logistics.common.shared.affiliation.AffiliationType;
import com.namequickly.logistics.common.shared.affiliation.CompanyAffiliation;
import com.namequickly.logistics.common.shared.affiliation.CourierAffiliation;
import com.namequickly.logistics.common.shared.affiliation.HubAffiliation;
import com.namequickly.logistics.common.shared.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Arrays;
import java.util.UUID;

// TODO : 어노테이션마다 각각 메시지를 적어주지 않으면 @NotBlank 에서 걸리면 기본 에러 내용이 표시되게 됩니다. 그리고 주사용자가 한국인인 만큼 한국어로 적는게 좋을 것 같아요. 아니면 국제화(i18n)에 대해 알아보는 것도 좋아보여요!

public record UserSignupRequestDto(
    @NotBlank
        @Size(min = 4, max = 15)
        @Pattern(regexp = "^[a-z0-9]*$", message = "Username should only contain lowercase letters and digits.")
        String username,

    @NotBlank
        @Size(min = 8, max = 15)
        @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()]*$", message = "Password must include letters, numbers, and special characters.")
        String password,

        UserRole role, // 권한
        AffiliationType affiliationType, // 허브, 업체, 배송기사 타입
        UUID companyAffiliationId,
        UUID courierAffiliationId,
        UUID hubAffiliationId
) {
        
}
