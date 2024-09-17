package com.namequickly.logistics.product_company.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProductCreateRequestDto {

    private UUID hubId;
    private UUID supplierId;
    @NotBlank(message = "상품명은 필수 입력값입니다.")
    private String productName;
    @Size(max = 500, message = "상품 설명은 500자 이하로 입력해주세요")
    private String productDescription;
    @Min(value = 0, message = "재고 수량은 0 이상으로 입력해주세요")
    private Integer stockQuantity;


}
