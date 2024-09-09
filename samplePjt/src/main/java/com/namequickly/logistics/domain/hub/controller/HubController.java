package com.namequickly.logistics.domain.hub.controller;

import com.namequickly.logistics.domain.hub.dto.HubRequestDto;
import com.namequickly.logistics.domain.hub.dto.HubResponseDto;
import com.namequickly.logistics.domain.hub.service.HubService;
import com.namequickly.logistics.global.common.response.CommonResponse;
import com.namequickly.logistics.global.common.response.CommonResponse.CommonEmptyRes;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hubs")
@RequiredArgsConstructor
public class HubController {

    private final HubService hubService;

    @PostMapping
    public CommonResponse<HubResponseDto> createHub(@RequestBody HubRequestDto requestDto) {
        HubResponseDto hub = hubService.createHub(requestDto);
        return CommonResponse.success(hub);
    }

    @PutMapping("/{hubId}")
    public CommonResponse<HubResponseDto> updateHub(
        @PathVariable("hubId") UUID hubId,
        @RequestBody HubRequestDto requestDto) {
        HubResponseDto hub = hubService.updateHub(hubId, requestDto);
        return CommonResponse.success(hub);
    }

    @DeleteMapping("/{hubId}")
    public CommonResponse<CommonEmptyRes> deleteHub(@PathVariable("hubId") UUID hubId) {
        hubService.deleteHub(hubId);
        return CommonResponse.success();
    }

    @GetMapping("/{hubId}")
    public CommonResponse<HubResponseDto> getHub(@PathVariable("hubId") UUID hubId) {
        HubResponseDto hub = hubService.getHub(hubId);
        return CommonResponse.success(hub);
    }

    @GetMapping
    public CommonResponse<List<HubResponseDto>> listHubs() {
        List<HubResponseDto> hubs = hubService.listHubs();
        return CommonResponse.success(hubs);
    }
}
