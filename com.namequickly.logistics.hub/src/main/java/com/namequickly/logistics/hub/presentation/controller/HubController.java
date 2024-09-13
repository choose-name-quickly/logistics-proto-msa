package com.namequickly.logistics.hub.presentation.controller;

import com.namequickly.logistics.common.response.CommonResponse;
import com.namequickly.logistics.common.response.CommonResponse.CommonEmptyRes;
import com.namequickly.logistics.hub.application.dto.HubRequestDto;
import com.namequickly.logistics.hub.application.dto.HubResponseDto;
import com.namequickly.logistics.hub.application.service.HubService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
