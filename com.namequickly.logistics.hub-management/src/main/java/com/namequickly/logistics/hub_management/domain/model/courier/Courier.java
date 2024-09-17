package com.namequickly.logistics.hub_management.domain.model.courier;


import com.namequickly.logistics.hub_management.presentation.dto.courier.CourierRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "p_courier", schema="hub_management")
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID courierId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourierType type;

    // 외부 서비스로 호출해 값을 받아옴
    private UUID hubId;

    private String name;

    private String address;
    private String phone;
    // TODO: 디폴트값을 줘야 하는가?
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourierStatus status;

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


    // TODO : protected인 이유?
    @PrePersist
    protected void onCreate() {
        createdAt = Timestamp.valueOf(LocalDateTime.now());
    }

    @PreUpdate
    protected void onUpdate() { updatedAt = Timestamp.valueOf(LocalDateTime.now());}

    public static Courier create(CourierRequest request, UUID userId){
        return Courier.builder()
                .type(request.type())
                .hubId(request.hubId())
                .name(request.name())
                .address(request.address())
                .phone(request.phone())
                .status(request.status())
                .createdBy(userId)
                .build();
    }

    public void update(CourierRequest request, UUID userId) {
        this.hubId = request.hubId();
        this.address = request.address();
        this.phone = request.phone();
        this.status = request.status();
        this.updatedBy = userId;
    }

    public void delete(UUID userId) {
        this.deletedAt = Timestamp.valueOf(LocalDateTime.now());
        this.deletedBy = userId;
        this.isDelete = true;
    }
}
