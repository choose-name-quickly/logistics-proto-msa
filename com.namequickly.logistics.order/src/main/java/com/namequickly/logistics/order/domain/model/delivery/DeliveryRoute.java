package com.namequickly.logistics.order.domain.model.delivery;

import com.namequickly.logistics.order.domain.model.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "p_delivery_route")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Getter
public class DeliveryRoute extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID deliveryRouteId;

    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @Column(name = "actual_distance", precision = 10, scale = 2)
    @ColumnDefault("0")
    private BigDecimal actualDistance;

    @Column(name = "actual_duration", precision = 5, scale = 2)
    @ColumnDefault("0")
    private BigDecimal actualDuration;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status", nullable = false)
    @ColumnDefault("'HUB_WAITING'")
    private DeliveryStatus deliveryStatus;

    // 외부 서버로 받아온 값
    private UUID routeHubId;

    // 외부 서버로 받아온 값
    private UUID courierId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // TODO 이걸 키값 객체로 줄 수 있지 않을까?
    // 이거 주석 해야 해나 ?
    @Transient
    private LocalDateTime actualStartTime;
    @Transient
    private LocalDateTime actualEndTime;


    @Column(name = "is_delete", nullable = false)
    @ColumnDefault("false")
    private Boolean isDelete;

    // TODO create 메서드 수정

    public static DeliveryRoute create(Integer sequence, UUID routeHubId, UUID courierId,
        Delivery delivery) {
        return DeliveryRoute.builder()
            .sequence(sequence)
            .routeHubId(routeHubId)
            .courierId(courierId)
            .delivery(delivery)
            .isDelete(false)
            .build();
    }

    public void assignDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    // TODO update 메서드 추가
    // 허브 출발
    public void departFromHub() {
        this.deliveryStatus = DeliveryStatus.HUB_IN_TRANSIT;
        this.actualStartTime = LocalDateTime.now();
    }

    // 허브 도착
    public void arriveAtHub(BigDecimal actualDistance) {
        this.deliveryStatus = DeliveryStatus.HUB_ARRIVED;
        this.actualDistance = actualDistance;
        this.actualEndTime = LocalDateTime.now();
        this.actualDuration = getActualDuration();
    }

    // 마지막 배달 시작
    public void departForCompany() {
        this.deliveryStatus = DeliveryStatus.OUT_FOR_DELIVERY;
        this.actualStartTime = LocalDateTime.now();
    }

    // 마지막 배달 완료
    public void arriveAtCompany(BigDecimal actualDistance) {
        this.deliveryStatus = DeliveryStatus.DELIVERED;
        this.actualDistance = actualDistance;
        this.actualEndTime = LocalDateTime.now();
        this.actualDuration = getActualDuration();
    }

    public BigDecimal getActualDuration() {
        long durationInSeconds = java.time.Duration.between(actualStartTime, actualEndTime)
            .getSeconds();
        return BigDecimal.valueOf(durationInSeconds)
            .divide(BigDecimal.valueOf(3600), 2, RoundingMode.HALF_UP);
    }

    // 배송 취소
    public void cancelDeliveryRoute(String userName) {
        this.isDelete = true;
        deleteEntity(userName);
    }
}
