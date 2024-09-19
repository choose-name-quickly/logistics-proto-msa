package com.namequickly.logistics.hub.presentation.controller;

import com.namequickly.logistics.common.response.CommonResponse;
import com.namequickly.logistics.common.response.CommonResponse.CommonEmptyRes;
import com.namequickly.logistics.hub.application.dto.HubRequestDto;
import com.namequickly.logistics.hub.application.dto.HubResponseDto;
import com.namequickly.logistics.hub.application.service.HubService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hubs")
@RequiredArgsConstructor
@Slf4j(topic = "hubController")
public class HubController {

    private final HubService hubService;

    @PostMapping("one")
    public CommonResponse<HubResponseDto> createHub(@RequestBody HubRequestDto requestDto,

        @RequestHeader(value = "X-User-Name", required = false) String username,
        @RequestHeader(value = "X-User-Roles", required = false) String roles,
        @RequestHeader(value = "X-User-AffiliationType", required = false) String affiliationType,
        @RequestHeader(value = "X-User-AffiliationId", required = false) String affiliationId) {

        log.info("createHub() requestDto = {} ", requestDto.toString());
        log.info("from gateway , username={}, roles={}, affiliationType={}, affiliationId={}", username, roles, affiliationType, affiliationId);

        HubResponseDto hub = hubService.createHub(requestDto);
        return CommonResponse.success(hub);

    }

    @PostMapping("many")
    public CommonResponse<List<HubResponseDto>> createHubs(@RequestBody List<HubRequestDto> requestDtos){
        log.info("createHubs() requestDtos = {} ", requestDtos.toString());

        List<HubResponseDto> hubs = hubService.createHubs(requestDtos);
        return CommonResponse.success(hubs);

    }

    @PutMapping("/{hubId}")
    public CommonResponse<HubResponseDto> updateHub(
        @PathVariable("hubId") UUID hubId,
        @RequestBody HubRequestDto requestDto) {
        log.info("updateHub() requestDto = {} ", requestDto);

        HubResponseDto hub = hubService.updateHub(hubId, requestDto);
        return CommonResponse.success(hub);
    }

    @DeleteMapping("/{hubId}")
    public CommonResponse<CommonEmptyRes> deleteHub(@PathVariable("hubId") UUID hubId) {
        log.info("deleteHub() hubId = {} ", hubId);

        hubService.deleteHub(hubId);
        return CommonResponse.success();
    }

    @GetMapping("/{hubId}")
    public CommonResponse<HubResponseDto> getHub(@PathVariable("hubId") UUID hubId) {
        log.info("getHub() hubId = {} ", hubId);

        HubResponseDto hub = hubService.getHub(hubId);
        return CommonResponse.success(hub);
    }

    @GetMapping
    public CommonResponse<List<HubResponseDto>> listHubs() {
        log.info("listHubs()");

        List<HubResponseDto> hubs = hubService.listHubs();
        return CommonResponse.success(hubs);
    }
}
