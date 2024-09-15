package com.namequickly.logistics.order.presentation.dto;

import com.namequickly.logistics.order.application.dto.OrderUpdateDto;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderUpdateRequestDto {

    private UUID productId;
    private Integer orderQuantity;

    public OrderUpdateDto toDTO() {
        return OrderUpdateDto.create(this.productId, this.orderQuantity);
    }
}
