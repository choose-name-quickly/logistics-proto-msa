package com.namequickly.logistics.hub.application.service;

import com.namequickly.logistics.common.exception.GlobalException;
import com.namequickly.logistics.common.response.ResultCase;
import com.namequickly.logistics.common.shared.affiliation.HubAffiliation;
import com.namequickly.logistics.hub.application.dto.HubRequestDto;
import com.namequickly.logistics.hub.application.dto.HubResponseDto;
import com.namequickly.logistics.hub.application.mapper.HubMapper;
import com.namequickly.logistics.hub.domain.model.Hub;
import com.namequickly.logistics.hub.domain.repository.HubRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * (Feign) 존재하는 허브ID 인지 체크
     */
    public boolean checkHubExists(UUID hubId) {
        return hubRepository.existsById(hubId);
    }

    /**
     * (Feign) affiliationId 기반으로 허브ID 리턴
     */
    public UUID getHubIdByAffiliationId(HubAffiliation affiliationId) {
        Hub hub = hubRepository.findHubIdByAffiliationId(affiliationId)
            .orElseThrow(()-> new GlobalException(ResultCase.HUB_NOT_FOUND));

        return hub.getHubId();
    }

}
