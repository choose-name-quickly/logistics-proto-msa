package com.namequickly.logistics.product_company.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreateResponseDto {

    private UUID productId;
    private LocalDateTime createdAt;
}
