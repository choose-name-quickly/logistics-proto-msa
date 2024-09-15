package com.namequickly.logistics.order.domain.model.delivery;

import lombok.Getter;

@Getter
public enum DeliveryStatus {
    HUB_WAITING,        // 허브 대기 중
    HUB_IN_TRANSIT,     // 허브 이동 중
    HUB_ARRIVED,        // 허브 도착
    OUT_FOR_DELIVERY,   // 배달 중
    DELIVERED           // 배달 완료


}
