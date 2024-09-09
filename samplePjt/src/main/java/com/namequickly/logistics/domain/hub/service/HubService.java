package com.namequickly.logistics.domain.hub.service;

import com.namequickly.logistics.domain.hub.domain.Hub;
import com.namequickly.logistics.domain.hub.dto.HubRequestDto;
import com.namequickly.logistics.domain.hub.dto.HubResponseDto;
import com.namequickly.logistics.domain.hub.mapper.HubMapper;
import com.namequickly.logistics.domain.hub.repository.HubRepository;
import com.namequickly.logistics.global.exception.GlobalException;
import com.namequickly.logistics.global.common.response.ResultCase;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HubService {

    private final HubRepository hubRepository;

    @Autowired
    private HubMapper hubMapper;

    public HubResponseDto createHub(HubRequestDto requestDto) {
        Hub hub = hubMapper.toEntity(requestDto);
        Hub savedHub = hubRepository.save(hub);
        return hubMapper.toDTO(savedHub);
    }

    public HubResponseDto updateHub(UUID hubId, HubRequestDto requestDto) {
        Hub hub = hubRepository.findById(hubId)
            .orElseThrow(() -> new GlobalException(ResultCase.NOT_FOUND));
        hub.updateHub(requestDto);
        Hub updatedHub = hubRepository.save(hub);
        return hubMapper.toDTO(updatedHub);
    }

    public void deleteHub(UUID hubId) {
        Hub hub = hubRepository.findById(hubId)
            .orElseThrow(() -> new GlobalException(ResultCase.NOT_FOUND));
        hubRepository.delete(hub);
    }

    public HubResponseDto getHub(UUID hubId) {
        Hub hub = hubRepository.findById(hubId)
            .orElseThrow(() -> new GlobalException(ResultCase.NOT_FOUND));
        return hubMapper.toDTO(hub);
    }

    public List<HubResponseDto> listHubs() {
        List<Hub> hubs = hubRepository.findAll();
        return hubs.stream()
            .map(hubMapper::toDTO)
            .toList();
    }
}
