package com.namequickly.logistics.order.application.service;

import com.namequickly.logistics.common.exception.GlobalException;
import com.namequickly.logistics.common.response.ResultCase;
import com.namequickly.logistics.order.application.dto.DeliveryGetResponseDto;
import com.namequickly.logistics.order.application.mapper.DeliveryMapper;
import com.namequickly.logistics.order.domain.model.delivery.Delivery;
import com.namequickly.logistics.order.infrastructure.repository.DeliveryRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;

    /**
     * 배송 정보 단건 구하기
     *
     * @param deliveryId
     * @return
     */
    public DeliveryGetResponseDto getDelivery(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findByDeliveryIdAndIsDeleteFalse(deliveryId)
            .orElseThrow(
                () -> new GlobalException(ResultCase.NOT_FOUND_DELIVERY)
            );
        return deliveryMapper.toDeliveryGetResponseDto(delivery);
    }


}
