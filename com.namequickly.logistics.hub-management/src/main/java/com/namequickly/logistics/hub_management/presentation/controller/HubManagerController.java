package com.namequickly.logistics.hub_management.presentation.controller;


import com.namequickly.logistics.hub_management.application.dto.HubManagerResponse;
import com.namequickly.logistics.hub_management.application.exception.ErrorResponse;
import com.namequickly.logistics.hub_management.application.exception.HubNotFoundException;
import com.namequickly.logistics.hub_management.application.service.HubManagerService;
import com.namequickly.logistics.hub_management.domain.repository.hubmanager.HubManagerRepo;
import com.namequickly.logistics.hub_management.presentation.dto.hubmanager.HubManagerRequest;
import com.namequickly.logistics.hub_management.presentation.dto.hubmanager.HubManagerSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/hubmanagers")
public class HubManagerController {

    private final HubManagerService hubManagerService;

    public HubManagerController(final HubManagerService hubManagerService) {
        this.hubManagerService = hubManagerService;
    }


    // TODO : gateway에서 로그인 부분 체크하고 파라미터 수정
    // 허브 매니저 등록
    @PostMapping
    public ResponseEntity<HubManagerResponse> createHubManager(@RequestBody final HubManagerRequest request,
                                                               @RequestHeader(value = "X-User-Id") UUID userId,
                                                               @RequestHeader(value = "X-Role") String role) {
        // 마스터만 권한 있음
        if(!"ROLE_MASTER".equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
        return ResponseEntity.ok(hubManagerService.createHubManager(request, userId));
    }

    // 허브 아이디 예외 처리
    @ExceptionHandler(HubNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleHubNotFoundException(HubNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    // 허브 매니저 수정
    @PatchMapping("/{managerId}")
    public HubManagerResponse updateHubManager(@PathVariable UUID managerId,
                                               @RequestBody final HubManagerRequest request,
                                               @RequestHeader(value = "X-User-Id") UUID userId,
                                               @RequestHeader(value = "X-Role") String role,
                                               @RequestHeader(value = "X-Affiliation-Id") UUID affiliationId) {
        // 마스터 + 허브 매니저 본인만
        if("ROLE_MASTER".equals(role)) {
            return hubManagerService.updateHubManager(managerId, request, userId);
        } else if("ROLE_HUB_MANAGER".equals(role) && managerId.equals(affiliationId)) {
            return hubManagerService.updateHubManager(managerId, request, userId);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
    }

    // 허브 매니저 삭제
    // 삭제 결과는 T/F
    @DeleteMapping("/{managerId}")
    public Boolean deleteHubManager(@PathVariable UUID managerId,
                                 @RequestHeader(value = "X-User-Id") UUID userId,
                                 @RequestHeader(value = "X-Role") String role) {
        if(!"ROLE_MASTER".equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
        return hubManagerService.deleteHubManager(managerId, userId);
    }

    // 허브 매니저 전체 조회
    @GetMapping
    public Page<HubManagerResponse> getHubManagers(@RequestHeader(value = "X-Role") String role,
                                                   @RequestBody HubManagerSearch search,
                                                   @RequestParam("page") int page,
                                                   @RequestParam("size") int size){
        if(!"ROLE_MASTER".equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
        Pageable pageable = PageRequest.of(page, size);
        return hubManagerService.getHubManagers(search, pageable);
    }

    // 허브 매니저 상세 조회
    @GetMapping("/{managerId}")
    public HubManagerResponse getHubManagerById(@PathVariable UUID managerId,
                                                @RequestHeader(value = "X-Role") String role,
                                                @RequestHeader(value = "X-Affiliation-Id") UUID affiliationId) {
        if("ROLE_MASTER".equals(role)) {
            return hubManagerService.getHubManagerById(managerId);
        } else if("HUB_MANAGER".equals(role)  && managerId.equals(affiliationId)) {
            return hubManagerService.getHubManagerById(managerId);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
    }

    // 허브 매니저 ID
    @GetMapping("/affiliationIds")
    public List<UUID> affiliationIds() {
        return hubManagerService.getMangerIds();
    }
}
