package com.namequickly.logistics.common.shared.affiliation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter

public enum HubAffiliation {

    // 허브 ID
    SEOUL("서울특별시 센터", "서울특별시 송파구 송파대로 55"),
    GYEONGGI_NORTH("경기 북부 센터", "경기도 고양시 덕양구 권율대로 570"),
    GYEONGGI_SOUTH("경기 남부 센터", "경기도 이천시 덕평로 257-21"),
    BUSAN("부산광역시 센터", "부산 동구 중앙대로 206"),
    DAEGU("대구광역시 센터", "대구 북구 태평로 161"),
    INCHEON("인천광역시 센터", "인천 남동구 정각로 29"),
    GWANGJU("광주광역시 센터", "광주 서구 내방로 111"),
    DAEJEON("대전광역시 센터", "대전 서구 둔산로 100"),
    ULSAN("울산광역시 센터", "울산 남구 중앙로 201"),
    SEJONG("세종특별자치시 센터", "세종특별자치시 한누리대로 2130"),
    GANGWON("강원특별자치도 센터", "강원특별자치도 춘천시 중앙로 1"),
    CHUNGBUK("충청북도 센터", "충북 청주시 상당구 상당로 82"),
    CHUNGNAM("충청남도 센터", "충남 홍성군 홍북읍 충남대로 21"),
    JEONBUK("전북특별자치도 센터", "전북특별자치도 전주시 완산구 효자로 225"),
    JEONNAM("전라남도 센터", "전남 무안군 삼향읍 오룡길 1"),
    GYEONGBUK("경상북도 센터", "경북 안동시 풍천면 도청대로 455"),
    GYEONGNAM("경상남도 센터", "경남 창원시 의창구 중앙대로 300");

    private final String name;
    private final String address;

}
