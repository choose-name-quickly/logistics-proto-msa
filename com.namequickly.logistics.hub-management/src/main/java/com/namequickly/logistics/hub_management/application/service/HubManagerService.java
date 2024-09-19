package com.namequickly.logistics.hub_management.application.service;

import com.namequickly.logistics.hub_management.application.dto.HubManagerListResponse;
import com.namequickly.logistics.hub_management.application.dto.HubManagerResponse;
import com.namequickly.logistics.hub_management.domain.model.hubmanager.HubManager;
import com.namequickly.logistics.hub_management.domain.repository.hubmanager.HubManagerRepo;
import com.namequickly.logistics.hub_management.presentation.dto.hubmanager.HubManagerRequest;
import com.namequickly.logistics.hub_management.presentation.dto.hubmanager.HubManagerSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class HubManagerService {

    // TODO: 생성자 주입이랑 @RequiredArgsConstructor 중에 무슨 방식이 더 나을까?
    private final HubManagerRepo hubManagerRepo;

    public HubManagerService(HubManagerRepo hubManagerRepo) {
        this.hubManagerRepo = hubManagerRepo;
    }

    // 허브 매니저 등록
    @Transactional
    public HubManagerResponse createHubManager(HubManagerRequest request, String username) {
        HubManager hubManager = HubManager.create(request, username);
        return HubManagerResponse.toResponse(hubManagerRepo.save(hubManager));
    }


    // TODO: common의 예외처리가 가능한지 확인
    // 허브 매니저 수정
    @Transactional
    public HubManagerResponse updateHubManager(UUID managerId, HubManagerRequest request, String username) {
        HubManager hubManager = hubManagerRepo.findById(managerId)
            .filter(p -> !p.getIsDelete()) // 삭제가 안된 것만
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 정보를 찾을 수 없거나 이미 삭제된 상태입니다."));

        hubManager.update(request, username);
        return HubManagerResponse.toResponse(hubManager);
    }

    // 허브 매니저 삭제
    @Transactional
    public Boolean deleteHubManager(UUID managerId, String username) {
        try {
           HubManager hubManager = hubManagerRepo.findById(managerId)
                  .filter(p -> !p.getIsDelete())
                  .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 정보를 찾을 수 없거나 이미 삭제된 상태입니다."));

           hubManager.delete(username);
           return true;
        } catch (Exception e) { return false; }
    }

    // 허브 매니저 전체 조회
    // TODO: 전체 조회 시에 @Transactional 안 붙이는 이유?
    public Page<HubManagerListResponse> getHubManagers(HubManagerSearch search, Pageable pageable) {
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

    //허브 매니저 ID 체크
    @Transactional
    public boolean checkId(UUID managerId) {
    return hubManagerRepo.checkId(managerId) != null;
}

    //허브 매니저 소속 허브
    public UUID getHubId(UUID managerId) {
    return hubManagerRepo.findHubId(managerId);
}
}