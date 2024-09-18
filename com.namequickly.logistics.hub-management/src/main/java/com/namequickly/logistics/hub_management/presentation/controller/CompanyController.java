package com.namequickly.logistics.hub_management.presentation.controller;

import com.namequickly.logistics.hub_management.application.dto.CompanyResponse;
import com.namequickly.logistics.hub_management.application.exception.ErrorResponse;
import com.namequickly.logistics.hub_management.application.exception.HubNotFoundException;
import com.namequickly.logistics.hub_management.application.service.CompanyService;
import com.namequickly.logistics.hub_management.presentation.dto.company.CompanyRequest;
import com.namequickly.logistics.hub_management.presentation.dto.company.CompanySearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(final CompanyService companyService) {
        this.companyService = companyService;
    }


    // TODO : 권한 체크를 컨트롤러와 서비스 중 어디서 하는 것이 더 좋을까?
    // TODO : gateway에서 로그인 부분 체크하고 파라미터 수정
    // 업체 등록
    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(@RequestBody final CompanyRequest request,
                                                         @RequestHeader(value = "X-User-Id") UUID userId,
                                                         @RequestHeader(value = "X-Role") String role,
                                                         @RequestHeader(value = "X-Hub-Id") UUID hubId) {

        // TODO : USER에서 소속 허브 반환하는 메서드가 있어야
        if ("ROLE_MASTER".equals(role) ) {
            return ResponseEntity.ok(companyService.createCompany(request, userId));
        } else if("ROLE_HUB_MANAGER".equals(role) && (request.hubId()).equals(hubId)) {
            return ResponseEntity.ok(companyService.createCompany(request, userId));
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
    }

    // 허브 아이디 예외 처리
    @ExceptionHandler(HubNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleHubNotFoundException(HubNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    // 업체 수정
    @PatchMapping("/{companyId}")
    public CompanyResponse updateCompany(@PathVariable UUID companyId,
                                         @RequestBody final CompanyRequest request,
                                         @RequestHeader(value = "X-User-Id") UUID userId,
                                         @RequestHeader(value = "X-Role") String role,
                                         @RequestHeader(value = "X-Affiliation-Id") UUID affiliationId,
                                         @RequestHeader(value = "X-Hub-Id") UUID hubId) {

        if ("ROLE_MASTER".equals(role)) {
            return companyService.updateCompany(companyId, request, userId);
        } else if("ROLE_HUB_MANAGER".equals(role) && (request.hubId()).equals(hubId)) {
            return companyService.updateCompany(companyId, request, userId);
        } // TODO : USER의 소속아이디를 체크할 수 있게 auth서비스에서 구현해야함
        else if ("ROLE_COMPANY".equals(role) && companyId.equals(affiliationId)) {
            return companyService.updateCompany(companyId, request, userId);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
    }

    // 업체 삭제
    @DeleteMapping("/{companyId}")
    public Boolean deleteCompany(@PathVariable UUID companyId,
                                 @RequestHeader(value = "X-User-Id") UUID userId,
                                 @RequestHeader(value = "X-Role") String role,
                                 @RequestHeader(value = "X-Hub-Id") UUID hubId) {

        // 마스터, 소속 허브 매니저만 권한 있음
        if ("ROLE_MASTER".equals(role)) {
            return companyService.deleteCompany(companyId, userId);
        } // TODO: 삭제의 경우 request로 hubId를 가져오지 않아서
          // 레포지토리에서 해당 소속 id를 가져와 체크해야한다는 번거로움.
          // 서비스에서 체크해야하나? 고민해봐야함.&& (request.hubId()).equals(hubId))
        else if("ROLE_HUB_MANAGER".equals(role)) {
            return companyService.deleteCompany(companyId, userId);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
    }

    // 업체 전체 조회
    @GetMapping
    public Page<CompanyResponse> getCompanies(@RequestHeader(value = "X-Role") String role,
                                              @RequestBody CompanySearch search,
                                              @RequestParam("page") int page,
                                              @RequestParam("size") int size) {

        if ("ROLE_MASTER".equals(role) || "ROLE_HUB_MANAGER".equals(role) || "ROLE_COMPANY".equals(role)) {
            Pageable pageable = PageRequest.of(page, size);
            return companyService.getCompanies(search, pageable);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
    }

    // 업체 상세 조회
    @GetMapping("/{companyId}")
    public CompanyResponse getCompanyById(@PathVariable UUID companyId,
                                          @RequestHeader(value = "X-Role") String role) {

        if ("ROLE_MASTER".equals(role) || "ROLE_HUB_MANAGER".equals(role) || "ROLE_COMPANY".equals(role)) {
            return companyService.getCompanyById(companyId);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
    }

    // 업체 아이디
    @GetMapping("/affiliationIds")
    public List<UUID> affiliationIds() {
        return companyService.getCompanyIds();
    }
}
