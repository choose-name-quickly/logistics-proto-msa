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
@Table(name = "p_courier")
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
    private String createdBy;

    @Column
    @LastModifiedDate
    private Timestamp updatedAt;
    private String updatedBy;

    private Timestamp deletedAt;
    private String deletedBy;
    @Builder.Default
    private Boolean isDelete = false;


    // TODO : protected인 이유?
    @PrePersist
    protected void onCreate() {
        createdAt = Timestamp.valueOf(LocalDateTime.now());
    }

    @PreUpdate
    protected void onUpdate() { updatedAt = Timestamp.valueOf(LocalDateTime.now());}

    public static Courier create(CourierRequest request, String username){
        return Courier.builder()
                .type(request.type())
                .hubId(request.hubId())
                .name(request.name())
                .address(request.address())
                .phone(request.phone())
                .status(request.status())
                .createdBy(username)
                .build();
    }

    public void update(CourierRequest request, String username) {
        this.hubId = request.hubId();
        this.address = request.address();
        this.phone = request.phone();
        this.status = request.status();
        this.updatedBy = username;
    }

    public void delete(String username) {
        this.deletedAt = Timestamp.valueOf(LocalDateTime.now());
        this.deletedBy = username;
        this.isDelete = true;
    }
}
