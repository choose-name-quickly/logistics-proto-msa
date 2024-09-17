package com.namequickly.logistics.ai.infrastructure.repository;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="order")
public interface DeliveryClient {

    // TODO: order 서비스에서 배송 정보 가져오기
}
