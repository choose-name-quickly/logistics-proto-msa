package com.namequickly.logistics.order.presentation.controller;

import com.namequickly.logistics.common.response.CommonResponse;
import com.namequickly.logistics.order.application.dto.DeliveryGetResponseDto;
import com.namequickly.logistics.order.application.service.DeliveryService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DeliveryController {

    private final DeliveryService deliveryService;

    // 배송 단건 조회
    @GetMapping("/deliveries/{delivery_id}")
    public CommonResponse<DeliveryGetResponseDto> getDelivery(
        @PathVariable("delivery_id") UUID deliveryId) {
        return CommonResponse.success(deliveryService.getDelivery(deliveryId));
    }

    // 배송 상태 변경

    // 배송 전체 조회

    // 내 배송 전체 조회


}
