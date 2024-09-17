package com.namequickly.logistics.hub_management.domain.model.company;


import com.namequickly.logistics.hub_management.presentation.dto.company.CompanyRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "p_company", schema="hub_management")
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID companyId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CompanyType type;

    // 외부 서비스에서 호출해서 값 받아옴
    private UUID hubId;

    private String name;
    private String address;
    private String phone;

    @Column(updatable = false)
    @CreatedDate
    private Timestamp createdAt;
    private UUID createdBy;

    @Column
    @LastModifiedDate
    private Timestamp updatedAt;
    private UUID updatedBy;

    private Timestamp deletedAt;
    private UUID deletedBy;
    @Builder.Default
    private Boolean isDelete = false;



    @PrePersist
    protected void onCreate() {
        createdAt = Timestamp.valueOf(LocalDateTime.now());
    }

    @PreUpdate
    protected void onUpdate() { updatedAt = Timestamp.valueOf(LocalDateTime.now());}

    public static Company create(CompanyRequest request, UUID userId){

        return Company.builder()
                .type(request.type())
                .hubId(request.hubId())
                .name(request.name())
                .address(request.address())
                .phone(request.phone())
                .build();
    }

    public void update(CompanyRequest request, UUID userId) {
        this.address = request.address();
        this.phone = request.phone();
        this.updatedBy = userId;
    }

    public void delete(UUID userId) {
        this.deletedAt = Timestamp.valueOf(LocalDateTime.now());
        this.deletedBy = userId;
        this.isDelete = true;
    }
}
