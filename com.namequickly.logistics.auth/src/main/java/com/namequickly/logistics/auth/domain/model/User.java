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
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "p_user")
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "company_affiliation_id")
    private UUID companyAffiliationId;

    @Column(name = "courier_affiliation_id")
    private UUID courierAffiliationId;

    @Column(name = "hub_affiliation_id")
    private UUID hubAffiliationId;

    // 슬랙 ID
    @Column(name = "slack_id", length = 255)
    private String slackId;

    @Column(name = "is_public")
    private boolean isPublic;

    // 유저 생성 메서드
    public static User create(String username, String password, UserRole role,
        AffiliationType affiliationType,
        UUID companyAffiliationId,
        UUID courierAffiliationId,
        UUID hubAffiliationId) {

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
    public UUID getAffiliationId() {
        if (companyAffiliationId != null) {
            return companyAffiliationId;
        } else if (courierAffiliationId != null) {
            return courierAffiliationId;
        } else if (hubAffiliationId != null) {
            return hubAffiliationId;
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