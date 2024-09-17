package com.namequickly.logistics.order.application.service;

import com.namequickly.logistics.common.exception.GlobalException;
import com.namequickly.logistics.common.response.ResultCase;
import com.namequickly.logistics.order.application.dto.DeliveryGetByCourierRequestDto;
import com.namequickly.logistics.order.application.dto.DeliveryGetByCourierResponseDto;
import com.namequickly.logistics.order.application.dto.DeliveryGetResponseDto;
import com.namequickly.logistics.order.application.dto.client.DeliveryStatusUpdateRequestDto;
import com.namequickly.logistics.order.application.dto.client.DeliveryStatusUpdateResponseDto;
import com.namequickly.logistics.order.application.dto.client.HubDto;
import com.namequickly.logistics.order.application.dto.client.HubRouteDto;
import com.namequickly.logistics.order.application.mapper.DeliveryMapper;
import com.namequickly.logistics.order.domain.model.delivery.Delivery;
import com.namequickly.logistics.order.domain.model.delivery.DeliveryRoute;
import com.namequickly.logistics.order.domain.model.delivery.DeliveryStatus;
import com.namequickly.logistics.order.infrastructure.repository.DeliveryRepository;
import com.namequickly.logistics.order.infrastructure.repository.DeliveryRouteRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;
    private final DeliveryRouteRepository deliveryRouteRepository;

    private final FeignClientService feignClientService;

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
        // TODO 허브 경유 정보 받아와야 함
        // hub_id, distanceToNextHub, timeToNextHub

        // TODO 허브 정보 받아와야 함
        // hub_name, address

        return deliveryMapper.toDeliveryGetResponseDto(delivery);
    }

    /**
     * 배송 전체 정보 가져오기
     */
    public List<DeliveryGetResponseDto> getAllDelivery() {

        return null;
    }


    public List<DeliveryGetResponseDto> getAllMineDelivery(UUID affiliationId) {
        return null;
    }

    // feign client 용도

    /**
     * 배송 기사 별 배송 정보
     *
     * @param requestDtos
     * @return
     */
    @Transactional(readOnly = true)
    public List<DeliveryGetByCourierResponseDto> getAllDeliveriesByCourier(
        List<DeliveryGetByCourierRequestDto> requestDtos) {

        List<DeliveryGetByCourierResponseDto> responseDtos = new ArrayList<>();

        for (DeliveryGetByCourierRequestDto requestDto : requestDtos) {
            UUID courierId = requestDto.getCourierId();

            // 배달 경로에서 배달 기사로 조회 (배송 완료가 아닌 정보)
            List<DeliveryRoute> deliveryRoutes = deliveryRouteRepository.findByCourierId(courierId);

            List<DeliveryGetByCourierResponseDto.DeliveryGetByCourierDto> deliveryDtos = new ArrayList<>();

            for (DeliveryRoute deliveryRoute : deliveryRoutes) {
                long size = deliveryRepository.countByDeliveryId(
                    deliveryRoute.getDelivery().getDeliveryId());
                if (size == 0 || deliveryRoute.getDelivery().getDeliveryStatus()
                    .equals(DeliveryStatus.DELIVERED)) {
                    continue;
                }

                UUID deliveryRouteId = deliveryRoute.getRouteHubId();
                DeliveryStatus deliveryStatus = deliveryRoute.getDelivery().getDeliveryStatus();

                // 허브 정보 가져오기
                HubRouteDto hubRouteDto = feignClientService.getHubRoute(
                    deliveryRoute.getRouteHubId());
                UUID startRouteHubId = hubRouteDto.getHubId();
                BigDecimal distanceToNextHub = hubRouteDto.getDistanceToNextHub();
                BigDecimal timeToNextHub = hubRouteDto.getTimeToNextHub();
                int sequence = hubRouteDto.getOrderInRoute();

                // 시작 허브 정보
                HubDto startHubDto = feignClientService.getHub(startRouteHubId);
                String startHubName = startHubDto.getHubName();
                String startHubAddress = startHubDto.getHubAddress();

                UUID arriveRouteHubId = null;
                String arriveHubName = null;
                String arriveHubAddress = null;
                String recipientName = null;
                String recipientSlackId = null;
                String deliveryAddress = null;

                if (sequence == (size - 1)) {
                    // 업체 배송
                    recipientName = deliveryRoute.getDelivery().getRecipientName();
                    recipientSlackId = deliveryRoute.getDelivery().getRecipientSlackId();
                    deliveryAddress = deliveryRoute.getDelivery().getDeliveryAddress();
                } else {
                    // 허브 간 배송, 다음 허브 정보 가져오기
                    HubRouteDto nextHubRouteDto = feignClientService.getHubRoutes(
                            hubRouteDto.getRouteId())
                        .stream()
                        .filter(dto -> dto.getOrderInRoute() == sequence + 1)
                        .findFirst()
                        .orElse(null);

                    if (nextHubRouteDto != null) {
                        arriveRouteHubId = nextHubRouteDto.getHubId();
                        HubDto nextHubDto = feignClientService.getHub(arriveRouteHubId);
                        arriveHubName = nextHubDto.getHubName();
                        arriveHubAddress = nextHubDto.getHubAddress();
                    }
                }

                // DeliveryGetByCourierDto 객체 생성 및 값 설정
                DeliveryGetByCourierResponseDto.DeliveryGetByCourierDto deliveryDto = DeliveryGetByCourierResponseDto.DeliveryGetByCourierDto.builder()
                    .deliveryRouteId(deliveryRouteId)
                    .distanceToNextHub(distanceToNextHub)
                    .timeToNextHub(timeToNextHub)
                    .deliveryStatus(deliveryStatus)
                    .startRouteHubId(startRouteHubId)
                    .startHubName(startHubName)
                    .startHubAddress(startHubAddress)
                    .arriveRouteHubId(arriveRouteHubId)
                    .arriveHubName(arriveHubName)
                    .arriveHubAddress(arriveHubAddress)
                    .recipientName(recipientName)
                    .recipientSlackId(recipientSlackId)
                    .deliveryAddress(deliveryAddress)
                    .build();

                deliveryDtos.add(deliveryDto);
            }

            // 각 기사의 응답 DTO 추가
            DeliveryGetByCourierResponseDto responseDto = DeliveryGetByCourierResponseDto.builder()
                .courierId(courierId)
                .deliveries(deliveryDtos)
                .build();

            responseDtos.add(responseDto);
        }

        return responseDtos;
    }

    /**
     * 배송 상태 변경을 위한 메서드
     *
     * @param requestDto
     * @return
     */
    @Transactional(readOnly = false)
    public DeliveryStatusUpdateResponseDto updateDeliveryStatus(
        DeliveryStatusUpdateRequestDto requestDto) {

        DeliveryRoute deliveryRoute = deliveryRouteRepository.findByDeliveryRouteId(
                requestDto.getDeliveryRouteId())
            .orElseThrow(() -> new GlobalException(ResultCase.NOT_FOUND_DELIVERY_ROUTE));

        DeliveryStatus currentDeliveryStatus = deliveryRoute.getDelivery().getDeliveryStatus();
        DeliveryStatus updateDeliveryStatus = requestDto.getDeliveryStatus();

        long size = deliveryRepository.countByDeliveryId(
            deliveryRoute.getDelivery().getDeliveryId());

        HubRouteDto hubRouteDto = feignClientService.getHubRoute(deliveryRoute.getRouteHubId());
        int sequence = hubRouteDto.getOrderInRoute();

        if (sequence == (size - 1)) {
            // 업체 배송
            if (DeliveryStatus.HUB_IN_TRANSIT.equals(updateDeliveryStatus)) {
                if (!DeliveryStatus.HUB_WAITING.equals(currentDeliveryStatus)) {
                    throw new GlobalException(ResultCase.INVALID_DELIVERY_STATUS);
                }
                deliveryRoute.departFromHub();
            } else if (DeliveryStatus.HUB_ARRIVED.equals(updateDeliveryStatus)) {
                if (!DeliveryStatus.HUB_IN_TRANSIT.equals(currentDeliveryStatus)) {
                    throw new GlobalException(ResultCase.INVALID_DELIVERY_STATUS);
                }

                if (requestDto.getActualDistance() == null) {
                    throw new GlobalException(ResultCase.REQUIRED_ACTUAL_DISTANCE);
                }
                deliveryRoute.arriveAtHub(requestDto.getActualDistance());
            }
        } else {
            // 허브 배송
            if (DeliveryStatus.OUT_FOR_DELIVERY.equals(updateDeliveryStatus)) {
                if (!DeliveryStatus.HUB_WAITING.equals(currentDeliveryStatus)) {
                    throw new GlobalException(ResultCase.INVALID_DELIVERY_STATUS);
                }
                deliveryRoute.departForCompany();
            } else if (DeliveryStatus.HUB_ARRIVED.equals(updateDeliveryStatus)) {
                if (!DeliveryStatus.OUT_FOR_DELIVERY.equals(currentDeliveryStatus)) {
                    throw new GlobalException(ResultCase.INVALID_DELIVERY_STATUS);
                }

                if (requestDto.getActualDistance() == null) {
                    throw new GlobalException(ResultCase.REQUIRED_ACTUAL_DISTANCE);
                }

                deliveryRoute.arriveAtCompany(requestDto.getActualDistance());
            }

        }
        deliveryRouteRepository.flush();

        return DeliveryStatusUpdateResponseDto.builder()
            .deliveryRouteId(requestDto.getDeliveryRouteId())
            .courierId(requestDto.getCourierId())
            .beforeDeliveryStatus(currentDeliveryStatus)
            .afterDeliveryStatus(updateDeliveryStatus)
            .updatedAt(deliveryRoute.getUpdatedAt())
            .build();
    }


}
