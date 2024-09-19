package com.namequickly.logistics.order.application.service;

import com.namequickly.logistics.common.exception.GlobalException;
import com.namequickly.logistics.common.response.ResultCase;
import com.namequickly.logistics.order.application.dto.DeliveryGetByCourierResponseDto;
import com.namequickly.logistics.order.application.dto.DeliveryGetResponseDto;
import com.namequickly.logistics.order.application.dto.client.DeliveryStatusUpdateRequestDto;
import com.namequickly.logistics.order.application.dto.client.DeliveryStatusUpdateResponseDto;
import com.namequickly.logistics.order.application.dto.client.HubResponseDto;
import com.namequickly.logistics.order.application.dto.client.RouteHubResponseDto;
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
     * @return
     */
    @Transactional(readOnly = true)
    public List<DeliveryGetByCourierResponseDto> getAllHubwatingDeliveries() {
        // MapStruct 이용하려니까 너무 복잡해서 그냥 한땀 한땀 수동으로 매핑했습니다.ㅠㅠ
        // 배달 경로에서 배달 상태가 HUB_WAITING인 것만 조회
        List<DeliveryRoute> deliveryRoutes = deliveryRouteRepository.findAllByDeliveryStatusAndIsDeleteIsFalse(
            DeliveryStatus.HUB_WAITING);

        List<DeliveryGetByCourierResponseDto> responseDtos = new ArrayList<>();
        // TODO 허브 - 업체간 배달은 나중에 구현
        UUID lastHubId = null;

        // 배달 경로 가져오기
        for (DeliveryRoute deliveryRoute : deliveryRoutes) {
            // 초기화
            UUID arriveRouteHubId = null;
            String arriveHubName = null;
            String arriveHubAddress = null;
            String recipientName = null;
            String recipientSlackId = null;
            String deliveryAddress = null;
            long size = 0;

            // 배송 기사 정보 가져오기
            UUID courierId = deliveryRoute.getCourierId();

            // 배달 경로 정보 - 배달 경로 PK , 배달 상태
            UUID deliveryRouteId = deliveryRoute.getRouteHubId();
            DeliveryStatus deliveryStatus = deliveryRoute.getDelivery().getDeliveryStatus();

            // 배달 경로 정보 - 출발 허브 정보 가져오기
            RouteHubResponseDto hubRouteDto = feignClientService.getHubRoute(
                deliveryRoute.getRouteHubId());
            UUID startRouteHubId = hubRouteDto.getHubId();
            int sequence = hubRouteDto.getOrderInRoute();

            // 배달 경로 정보 - 예상 시간, 예상 경로
            BigDecimal distanceToNextHub = new BigDecimal(
                Double.toString(hubRouteDto.getDistanceToNextHub()));
            BigDecimal timeToNextHub = new BigDecimal(
                Double.toString(hubRouteDto.getTimeToNextHub()));

            // 허브 정보 - 출발 허브 상세 정보 (이름 / 주소)
            HubResponseDto startHubDto = feignClientService.getHub(startRouteHubId);
            String startHubName = startHubDto.getHubName();
            String startHubAddress = startHubDto.getAddress();

            // 배달 PK 에 대한 배달 경유가 몇 개인지
            size = deliveryRepository.countByDeliveryId(
                deliveryRoute.getDelivery().getDeliveryId());

            // 허브 간 배송
            arriveRouteHubId = hubRouteDto.getNextHubId();
            HubResponseDto nextHubDto = feignClientService.getHub(arriveRouteHubId);
            arriveHubName = nextHubDto.getHubName();
            arriveHubAddress = nextHubDto.getAddress();

            // TODO 허브 - 업체간 배달은 나중에 구현
            if (sequence == size) {
                // 업체 배송
                lastHubId = hubRouteDto.getNextHubId();
               /* // 수령인 , 수령 ID, 배달 주소
                recipientName = deliveryRoute.getDelivery().getRecipientName();
                recipientSlackId = deliveryRoute.getDelivery().getRecipientSlackId();
                deliveryAddress = deliveryRoute.getDelivery().getDeliveryAddress();*/
            }

            // DeliveryGetByCourierResponseDto 객체 생성
            DeliveryGetByCourierResponseDto responseDto = DeliveryGetByCourierResponseDto.builder()
                .courierId(courierId)
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

        RouteHubResponseDto hubRouteDto = feignClientService.getHubRoute(
            deliveryRoute.getRouteHubId());
        int sequence = hubRouteDto.getOrderInRoute();

      /* if (sequence == (size + 1)) {
            // 업체 배송
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
        } else {*/
        // 허브 배송
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
