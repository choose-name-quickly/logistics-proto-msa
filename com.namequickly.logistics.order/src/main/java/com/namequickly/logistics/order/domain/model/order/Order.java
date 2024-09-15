package com.namequickly.logistics.order.domain.model.order;

import com.namequickly.logistics.order.domain.model.common.BaseEntity;
import com.namequickly.logistics.order.domain.model.delivery.Delivery;
import com.namequickly.logistics.order.domain.model.delivery.DeliveryStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
import org.hibernate.annotations.DialectOverride.Wheres;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;


@Entity
@Table(name="p_order")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID orderId;

    // 외부 서버로 받아온 값
    private UUID supplierId;

    // 외부 서버로 받아온 값
    private UUID receiverId;


    @OneToOne(mappedBy="order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Delivery delivery;

    @OneToMany(mappedBy="order", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @BatchSize(size = 1000)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Column(name="is_delete", nullable = false)
    @ColumnDefault("false")
    private Boolean isDelete;


    /**
     *  주문 생성 메서드
     */
    public static Order create(UUID supplierId, UUID receiverId){
        Order order = Order.builder()
            .supplierId(supplierId)
            .receiverId(receiverId)
            .isDelete(false)
            .build();
        return order;
    }

    /**
     *  주문 상품 설정 편의 메서드
     */
    public void addOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
        orderProduct.assignOrder(this);
    }


    /**
     *  배송 설정 편의 메서드
     */
    public void addDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.assignOrder(this);
    }

    /**
     *  주문 취소 메서드
     */
    public void cancelOrder(String userName){
        this.isDelete = true;
        deleteEntity(userName);

        for(OrderProduct orderProduct : this.orderProducts){
            orderProduct.cancelOrderProduct(userName);
        }

        this.delivery.cancelDelivery(userName);
    }



    /**
     * 주문 수량 변경 메서드
     */
    public void updateOrderQuantity(UUID productId, Integer quantity){
        this.orderProducts.stream()
            .filter(orderProduct -> orderProduct.getProductId().equals(productId))
            .forEach(orderProduct -> orderProduct.updateOrderQuantity(quantity));
    }

    public DeliveryStatus getDeliveryStatus() {
        return this.delivery.getDeliveryStatus();
    }
}
