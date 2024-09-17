package com.namequickly.logistics.order.application.dto;

import com.namequickly.logistics.order.domain.model.delivery.DeliveryStatus;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryGetByCourierResponseDto {

    private UUID courierId; // 배달 기사 정보

    private UUID deliveryRouteId; // 배송 경유 Id
    private BigDecimal distanceToNextHub;  //예상거리
    private BigDecimal timeToNextHub; //예상시간
    private DeliveryStatus deliveryStatus; //배송 상태

    private UUID startRouteHubId;  // 출발 허브 ID
    private String startHubName; //허브 이름
    private String startHubAddress; // 허브 주소

    // 허브 배송
    private UUID arriveRouteHubId; // 도착 허브 ID
    private String arriveHubName; //허브 이름
    private String arriveHubAddress; // 허브 주소

    // 업체 배송
    // 만약 업체 배송 기사면 해당 부분 o
    private String recipientName;
    private String recipientSlackId;
    private String deliveryAddress;
    // 공통


}
