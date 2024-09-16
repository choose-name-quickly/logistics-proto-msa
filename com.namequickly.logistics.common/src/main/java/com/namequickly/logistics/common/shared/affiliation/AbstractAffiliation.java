package com.namequickly.logistics.common.shared.affiliation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractAffiliation implements Affiliation {
    private final String name;
    private final String address;
}

// 인터페이스는 enum의 실제 타입 정보를 제공하지 않는다
// Jackson은 enum 값을 문자열로 변환하거나 문자열을 enum으로 변환할 때, 구체적인 enum 클래스가 필요하다.
// 따라서 interface 의 구현체도 따로 만듦