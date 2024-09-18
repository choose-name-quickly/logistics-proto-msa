package com.namequickly.logistics.product_company.application.dto;


import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductGetResponseDto {

    private UUID productId;
    private UUID hubId;
    private String productName;
    private String productDescription;
    private Integer stockQuantity;
    private UUID supplierId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Getter(AccessLevel.NONE)
    private boolean isDelete;

    public Boolean getIsDelete() {
        return isDelete;
    }

}
