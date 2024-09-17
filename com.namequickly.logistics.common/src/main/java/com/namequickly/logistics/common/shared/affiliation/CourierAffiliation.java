package com.namequickly.logistics.common.shared.affiliation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter

public enum CourierAffiliation {

    // 스파르타 물류의 공통 허브 배송 담당자 ID
    COMMON_COURIER("공통 허브 배송 담당자", 10),

    // 각 허브의 업체 배송 담당자 ID
    SEOUL_COURIER("서울특별시 센터 배송 담당자", 10),
    GYEONGGI_NORTH_COURIER("경기 북부 센터 배송 담당자", 10),
    GYEONGGI_SOUTH_COURIER("경기 남부 센터 배송 담당자", 10),
    BUSAN_COURIER("부산광역시 센터 배송 담당자", 10),
    DAEGU_COURIER("대구광역시 센터 배송 담당자", 10),
    INCHEON_COURIER("인천광역시 센터 배송 담당자", 10),
    GWANGJU_COURIER("광주광역시 센터 배송 담당자", 10),
    DAEJEON_COURIER("대전광역시 센터 배송 담당자", 10),
    ULSAN_COURIER("울산광역시 센터 배송 담당자", 10),
    SEJONG_COURIER("세종특별자치시 센터 배송 담당자", 10),
    GANGWON_COURIER("강원특별자치도 센터 배송 담당자", 10),
    CHUNGBUK_COURIER("충청북도 센터 배송 담당자", 10),
    CHUNGNAM_COURIER("충청남도 센터 배송 담당자", 10),
    JEONBUK_COURIER("전북특별자치도 센터 배송 담당자", 10),
    JEONNAM_COURIER("전라남도 센터 배송 담당자", 10),
    GYEONGBUK_COURIER("경상북도 센터 배송 담당자", 10),
    GYEONGNAM_COURIER("경상남도 센터 배송 담당자", 10);

    private final String courierName;
    private final int numberOfCouriers;

}
