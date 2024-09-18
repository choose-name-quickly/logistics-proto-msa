package com.namequickly.logistics.hub.presentation.FeingController;

import com.namequickly.logistics.common.exception.GlobalException;
import com.namequickly.logistics.common.response.CommonResponse;
import com.namequickly.logistics.common.response.ResultCase;
import com.namequickly.logistics.common.shared.affiliation.HubAffiliation;
import com.namequickly.logistics.hub.application.dto.HubResponseDto;
import com.namequickly.logistics.hub.application.service.HubService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hubsFeign")
@RequiredArgsConstructor
@Slf4j(topic = "hubFeignController")
public class HubFeignController {

    private final HubService hubService;

    /**
     * 존재하는 허브인지 체크
     */
    @GetMapping("/{hubId}")
    public CommonResponse<String> checkHubExists(@PathVariable("hubId") UUID hubId) {
        if (!hubService.checkHubExists(hubId)) {
            throw new GlobalException(ResultCase.HUB_NOT_FOUND);
        }

        return CommonResponse.success("존재하는 허브입니다");
    }

    /**
     * hubId 입력시 허브 이름과 주소를 리턴 (기존 getHub 서비스 메소드를 활용하기위해 hub 모든 정보를 리턴시켰습니다)
     */
    @GetMapping("/{hubId}/details")
    public CommonResponse<HubResponseDto> getHubDetails(@RequestParam UUID hubId) {
        HubResponseDto hub = hubService.getHub(hubId);
        return CommonResponse.success(hub);
    }

    /**
     * affiliationId 에 해당하는 HubId 를 반환
     */
    @GetMapping("/{affiliationId}")
    public CommonResponse<UUID> getHubIdByAffiliationId(@PathVariable("affiliationId") HubAffiliation affiliationId) {
        UUID hubId = hubService.getHubIdByAffiliationId(affiliationId);
        return CommonResponse.success(hubId);
    }

}
