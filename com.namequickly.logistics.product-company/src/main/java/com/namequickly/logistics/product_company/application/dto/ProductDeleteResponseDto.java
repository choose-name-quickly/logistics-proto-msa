package com.namequickly.logistics.product_company.application.dto;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDeleteResponseDto {

    private String productId;
    private LocalDateTime deletedAt;

}
