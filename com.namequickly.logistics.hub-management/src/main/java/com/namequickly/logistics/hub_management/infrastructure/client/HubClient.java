package com.namequickly.logistics.hub_management.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;


// hub를 호출하는 코드 OpenFeign
@FeignClient(name = "hub")
public interface HubClient {


    // 실제 존재하는 허브 아이디인지 체크 여부
    @PostMapping("/api/hubs/checkHubId")
    boolean checkHubId(@RequestParam UUID hubId);
    // @PostMapping("/api/auth/{slackId}")
    // boolean checkSlackId(@PathVariable UUID slackId);

    // TODO: 실제 허브 서비스에 있는 GetMapping 메서드와 동일한 구성이어야 하는가? ResponseDto를 포함하여
    @GetMapping("/api/hubs/assignList")
    List<UUID> assignList();
}
