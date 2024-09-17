package com.namequickly.logistics.auth.domain.model;

import com.namequickly.logistics.common.shared.UserRole;
import com.namequickly.logistics.common.shared.affiliation.AffiliationType;
import com.namequickly.logistics.common.shared.affiliation.CompanyAffiliation;
import com.namequickly.logistics.common.shared.affiliation.CourierAffiliation;
import com.namequickly.logistics.common.shared.affiliation.HubAffiliation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_user")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class User extends BaseEntity {

    @Id
    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "nickname", length = 100)
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)  // This will store the enum name as a string in the database
    @Column(name = "role")
    private UserRole role;

    // 허브, 업체, 배송기사 중 선택
    @Enumerated(EnumType.STRING)
    @Column(name = "affiliation_type")
    private AffiliationType affiliationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_affiliation_id")
    private CompanyAffiliation companyAffiliationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "courier_affiliation_id")
    private CourierAffiliation courierAffiliationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "hub_affiliation_id")
    private HubAffiliation hubAffiliationId;

    @Column(name = "is_public")
    private boolean isPublic;

    // 유저 생성 메서드
    public static User create(String username, String password, UserRole role,
        AffiliationType affiliationType,
        CompanyAffiliation companyAffiliationId,
        CourierAffiliation courierAffiliationId,
        HubAffiliation hubAffiliationId) {

        User user = new User();
        user.username = username;
        user.password = password;
        user.role = role;
        user.affiliationType = affiliationType;

        switch (affiliationType) {
            case COMPANY:
                user.companyAffiliationId = companyAffiliationId;
                break;
            case COURIER:
                user.courierAffiliationId = courierAffiliationId;
                break;
            case HUB:
                user.hubAffiliationId = hubAffiliationId;
                break;
        }

        return user;
    }

    // Affiliation ID 중 하나를 반환하는 메서드 - UserDetailsImpl 에서 끌고와 토큰 만들때씀
    public String getAffiliationId() {
        if (companyAffiliationId != null) {
            return companyAffiliationId.name();
        } else if (courierAffiliationId != null) {
            return courierAffiliationId.name();
        } else if (hubAffiliationId != null) {
            return hubAffiliationId.name();
        }
        return null; // 모든 affiliationId가 null인 경우
    }

    // 회원정보 수정 메서드

    // 비밀번호 업데이트 메서드
    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    // 회원권한 변경 메서드
    public void updateRole(UserRole role) {
        this.role = role;
    }

    // 일반 유저를 가게 주인으로 변경

}