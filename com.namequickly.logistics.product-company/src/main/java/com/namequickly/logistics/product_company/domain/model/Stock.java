package com.namequickly.logistics.product_company.domain.model;

import com.namequickly.logistics.product_company.domain.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "p_stock")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Stock extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID stockId;


    @Column(name = "stock_quantity")
    @ColumnDefault(value = "0")
    @PositiveOrZero(message = "재고 수량은 0 이상이어야 합니다.")
    private Integer stockQuantity;

    @ColumnDefault("false")
    @Column(name = "is_delete")
    private Boolean isDelete;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


    // create
    public static Stock create(Integer stockQuantity, Product product) {
        return Stock.builder()
            .stockQuantity(stockQuantity)
            .product(product)
            .isDelete(false)
            .build();
    }

    // 편의 제공 메서드
 /*   public void assignProduct(Product product) {
        this.product = product;
        product.addStockQuantity(this);
    }*/

    // 수정 메서드
    public void updateStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    // 삭제 메서드
    public void deleteStock(String userName) {
        isDelete = true;
        deleteEntity(userName);
    }


}
