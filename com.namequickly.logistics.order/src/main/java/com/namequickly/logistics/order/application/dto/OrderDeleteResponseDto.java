package com.namequickly.logistics.order.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDeleteResponseDto {

    private UUID orderId;
    private LocalDateTime deletedAt;
}
