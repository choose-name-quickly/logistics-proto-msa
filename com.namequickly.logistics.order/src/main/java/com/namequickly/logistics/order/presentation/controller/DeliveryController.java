package com.namequickly.logistics.order.presentation.controller;

import com.namequickly.logistics.common.response.CommonResponse;
import com.namequickly.logistics.order.application.dto.DeliveryGetByCourierResponseDto;
import com.namequickly.logistics.order.application.dto.DeliveryGetResponseDto;
import com.namequickly.logistics.order.application.dto.client.DeliveryStatusUpdateRequestDto;
import com.namequickly.logistics.order.application.dto.client.DeliveryStatusUpdateResponseDto;
import com.namequickly.logistics.order.application.service.DeliveryService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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


    /**
     * 배송 전체 정보 가져오기
     */
    public CommonResponse<List<DeliveryGetResponseDto>> getAllDelivery() {

        return null;
    }


    /**
     * 내 배송 정보 전체 조회
     *
     * @param affiliationId
     * @return
     */
    public List<DeliveryGetResponseDto> getAllMineDelivery(UUID affiliationId) {
        return null;
    }

    // feign client 용

    /**
     * 대기 중 배송 정보
     */
    @GetMapping("/deliveries/delivery-status/waiting")
    public List<DeliveryGetByCourierResponseDto> getAllHubwatingDeliveries() {
        return deliveryService.getAllHubwatingDeliveries();
    }

    // 배송 상태 변경
    @PutMapping("/deliveries/delivery-status")
    public DeliveryStatusUpdateResponseDto updateDeliveryStatus(
        @RequestBody DeliveryStatusUpdateRequestDto requestDto) {
        return deliveryService.updateDeliveryStatus(requestDto);
    }
}
