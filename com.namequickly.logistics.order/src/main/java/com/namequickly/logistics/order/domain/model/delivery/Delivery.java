package com.namequickly.logistics.order.domain.model.delivery;

import com.namequickly.logistics.order.domain.model.common.BaseEntity;
import com.namequickly.logistics.order.domain.model.order.Order;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "p_delivery")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Delivery extends BaseEntity {


    private static final String PHONE_NUMBER_TRANSFER_TARGET = "-";

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID deliveryId;

    // 외부 서버로 받아온 값
    private UUID originHubId;

    // 외부 서버로 받아온 값
    private UUID destinationHubId;

    @Column(name = "recipient_name", nullable = false)
    private String recipientName;

    @Column(name = "recipient_slack_id", nullable = false)
    private String recipientSlackId;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'HUB_WAITING'")
    private DeliveryStatus DeliveryStatus;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @BatchSize(size = 1000)
    private List<DeliveryRoute> deliveryRoutes = new ArrayList<>();


    @Column(name = "is_delete", nullable = false)
    @ColumnDefault("false")
    private Boolean isDelete;

    // TODO create 메서드 수정 (phone 부분)

    public static Delivery create(UUID originHubId, UUID destinationHubId, String recipientName,
        String recipientSlackId, Order order) {

        return Delivery.builder()
            .originHubId(originHubId)
            .destinationHubId(destinationHubId)
            .recipientName(recipientName)
            .recipientSlackId(recipientSlackId)
            .order(order)
            .isDelete(false)
            .build();
    }


    // order 할당
    public void assignOrder(Order order) {
        this.order = order;
    }

    // 편의 메서드
    public void addDeliveryRoute(DeliveryRoute deliveryRoute) {
        this.deliveryRoutes.add(deliveryRoute);
        deliveryRoute.assignDelivery(this);
    }

    // 배송 취소
    public void cancelDelivery(String userName) {
        this.isDelete = true;
        deleteEntity(userName);

        for (DeliveryRoute deliveryRoute : this.deliveryRoutes) {
            deliveryRoute.cancelDeliveryRoute(userName);
        }
    }

    //private void transferPhoneNumberFormat(String recipientSlackId) {
    //    this.recipientSlackId = recipientSlackId.replaceAll(PHONE_NUMBER_TRANSFER_TARGET, "");
    //}
}
