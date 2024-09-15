package com.namequickly.logistics.product_company.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateResponseDto {

    private UUID productId;
    private UUID hubId;
    private UUID supplierId;
    private String productName;
    private String productDescription;
    private Integer stockQuantity;
    private LocalDateTime updatedAt;

}
