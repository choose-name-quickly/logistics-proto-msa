package com.namequickly.logistics.hub.infrastructure.client;


import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hub-management-service") // 유레카 클라이언트 이름
public interface HubClient {

    /**
     * routeHubId 들을 보내 배송기사들을 배정받아 엮어오는 메서드
     */
    @PostMapping("/api/couriers/assignCouriers")
    List<Map<UUID, UUID>> getHubIdAndCourierList(@RequestBody List<UUID> routeHubIds);

}
