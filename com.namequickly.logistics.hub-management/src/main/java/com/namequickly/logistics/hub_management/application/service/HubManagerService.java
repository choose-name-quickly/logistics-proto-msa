package com.namequickly.logistics.hub_management.application.service;

import com.namequickly.logistics.hub_management.application.dto.HubManagerResponse;
import com.namequickly.logistics.hub_management.application.exception.HubNotFoundException;
import com.namequickly.logistics.hub_management.domain.model.hubmanager.HubManager;
import com.namequickly.logistics.hub_management.domain.repository.hubmanager.HubManagerRepo;
import com.namequickly.logistics.hub_management.infrastructure.client.HubClient;
import com.namequickly.logistics.hub_management.presentation.dto.hubmanager.HubManagerRequest;
import com.namequickly.logistics.hub_management.presentation.dto.hubmanager.HubManagerSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class HubManagerService {

    // TODO: 생성자 주입이랑 @RequiredArgsConstructor 중에 무슨 방식이 더 나을까?
    private final HubManagerRepo hubManagerRepo;
    private final HubClient hubClient;

    public HubManagerService(HubManagerRepo hubManagerRepo, HubClient hubClient) {
        this.hubManagerRepo = hubManagerRepo;
        this.hubClient = hubClient;
    }

    // 허브 매니저 등록
    @Transactional
    public HubManagerResponse createHubManager(HubManagerRequest request, UUID userId) {
        // TODO : 실제 존재하는 hubId인지 체크, getHubId가 실제 호출하는 서비스에서 어떻게 구성되어 있는지 확인
        if(!hubClient.checkHubId(request.hubId())) {
            // 예외 처리, 컨트롤러에서 오류 처리
            throw new HubNotFoundException("해당 아이디를 찾을 수 없습니다.");
        } else {
            HubManager hubManager = HubManager.create(request, userId);
            return HubManagerResponse.toResponse(hubManagerRepo.save(hubManager));}
    }


    // TODO: common의 예외처리가 가능한지 확인
    // 허브 매니저 수정
    @Transactional
    public HubManagerResponse updateHubManager(UUID managerId, HubManagerRequest request, UUID userId) {
        HubManager hubManager = hubManagerRepo.findById(managerId)
                .filter(p -> !p.getIsDelete()) // 삭제가 안된 것만
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 정보를 찾을 수 없거나 이미 삭제된 상태입니다."));

        hubManager.update(request, userId);
        return HubManagerResponse.toResponse(hubManager);
    }

    // 허브 매니저 삭제
    @Transactional
    public Boolean deleteHubManager(UUID managerId, UUID userId) {
        try {
            HubManager hubManager = hubManagerRepo.findById(managerId)
                    .filter(p -> !p.getIsDelete())
                    .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 정보를 찾을 수 없거나 이미 삭제된 상태입니다."));

            hubManager.delete(userId);
            return true;
        } catch (Exception e) { return false; }
    }

    // 허브 매니저 전체 조회
    // TODO: 전체 조회 시에 @Transactional 안 붙이는 이유?
    public Page<HubManagerResponse> getHubManagers(HubManagerSearch search, Pageable pageable) {
        return hubManagerRepo.searchHubManagers(search, pageable);
    }

    // 허브 매니저 상세 조회
    // 지연 로딩
    @Transactional(readOnly = true)
    public HubManagerResponse getHubManagerById(UUID managerId) {
        HubManager hubManager = hubManagerRepo.findById(managerId)
                .filter(p -> !p.getIsDelete())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 정보를 찾을 수 없거나 이미 삭제된 상태입니다."));
        return HubManagerResponse.toResponse(hubManager);
    }

    //허브 매니저 아이디
    @Transactional
    public List<UUID> getMangerIds() {
        return hubManagerRepo.findAllHubManagerIds();
    }
}