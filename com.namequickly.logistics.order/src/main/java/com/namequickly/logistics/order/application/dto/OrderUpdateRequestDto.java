package com.namequickly.logistics.order.application.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdateRequestDto {

    private UUID productId;
    private Integer orderQuantity;

}
