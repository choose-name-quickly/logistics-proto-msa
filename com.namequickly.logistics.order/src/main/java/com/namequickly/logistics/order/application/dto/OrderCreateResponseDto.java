package com.namequickly.logistics.order.application.dto;

import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderCreateResponseDto {

    private UUID orderId;
    private LocalDate createdAt;
}
