package com.namequickly.logistics.product_company.domain.model;

import com.namequickly.logistics.product_company.domain.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "p_product")
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class Product extends BaseEntity {


    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID productId;

    @Column(name = "hub_id", nullable = false)
    private UUID hubId;

    @Column(name = "supplier_id", nullable = false)
    private UUID supplierId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "is_delete", nullable = false)
    @ColumnDefault("false")
    private Boolean isDelete;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Stock stock;

    // 생성
    public static Product create(UUID hubId, String productName, String productDescription,
        UUID supplierId) {
        return Product.builder()
            .hubId(hubId)
            .productName(productName)
            .productDescription(productDescription)
            .supplierId(supplierId)
            .isDelete(false)
            .build();
    }


    // 편의 관계 메서드
    public void addStockQuantity(Stock stock) {
        this.stock = stock;
    }

    // 상품 삭제 (소프트 딜리트)
    public void deleteProduct(String userName) {
        this.isDelete = true;
        deleteEntity(userName);

        stock.deleteStock(userName);
    }


    // 상품 수정
    public void updateProduct(String productName, String productDescription,
        Integer stockQuantity) {
        this.productName = productName;
        this.productDescription = productDescription;
        if (stockQuantity != null) {
            this.stock.updateStockQuantity(stockQuantity);
        }
    }

}
