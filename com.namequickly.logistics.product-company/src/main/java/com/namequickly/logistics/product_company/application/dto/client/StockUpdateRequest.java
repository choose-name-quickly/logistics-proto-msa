package com.namequickly.logistics.product_company.application.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockUpdateRequest {

    private Integer stockQuantity;
    private OperationType operationType; // "INCREASE" or "DECREASE"
}
