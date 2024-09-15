package com.namequickly.logistics.order.domain.model.order;

import com.namequickly.logistics.order.domain.model.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "p_order_product")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Getter
public class OrderProduct extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID orderProductId;

    @Column(name = "order_quantity", nullable = false)
    private Integer orderQuantity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    // 외부 서버로 받아온 값
    private UUID productId;


    @Column(name = "is_delete", nullable = false)
    @ColumnDefault("false")
    private Boolean isDelete;


    public static OrderProduct create(Order order, UUID productId, int orderQuantity) {
        OrderProduct orderProduct = OrderProduct.builder()
            .order(order)
            .productId(productId)
            .orderQuantity(orderQuantity)
            .isDelete(false)
            .build();

        return orderProduct;

    }

    // order 할당
    public void assignOrder(Order order) {
        this.order = order;
    }

    // 상품 삭제
    public void cancelOrderProduct(String userName) {
        this.isDelete = true;
        deleteEntity(userName);
    }

    public void updateOrderQuantity(Integer quantity) {
        this.orderQuantity = quantity;
    }

}
