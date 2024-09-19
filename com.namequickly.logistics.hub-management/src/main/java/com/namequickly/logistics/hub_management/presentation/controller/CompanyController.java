package com.namequickly.logistics.hub_management.presentation.controller;

import com.namequickly.logistics.hub_management.application.dto.CompanyListResponse;
import com.namequickly.logistics.hub_management.application.dto.CompanyResponse;
import com.namequickly.logistics.hub_management.application.exception.ErrorResponse;
import com.namequickly.logistics.hub_management.application.exception.HubNotFoundException;
import com.namequickly.logistics.hub_management.application.service.CompanyService;
import com.namequickly.logistics.hub_management.application.service.HubManagerService;
import com.namequickly.logistics.hub_management.presentation.dto.company.CompanyRequest;
import com.namequickly.logistics.hub_management.presentation.dto.company.CompanySearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final HubManagerService hubManagerService;

    public CompanyController(final CompanyService companyService, final HubManagerService hubManagerService) {
        this.companyService = companyService;
        this.hubManagerService = hubManagerService;
    }

    // 업체 등록
    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(@RequestBody final CompanyRequest request,
                                                         @RequestHeader(value = "X-User-Name") String username,
                                                         @RequestHeader(value = "X-User-Role") String role,
                                                         @RequestHeader(value = "X-User-AffiliationId") UUID affiliationId) {

        if ("ROLE_MASTER".equals(role) ) return ResponseEntity.ok(companyService.createCompany(request, username));
        else if("ROLE_HUBMANAGER".equals(role)) {
            // 같은 소속 허브 매니저만
            if(hubManagerService.getHubId(affiliationId).equals(request.hubId())) return ResponseEntity.ok(companyService.createCompany(request, username));
            else   throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        } else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
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
    public CompanyResponse updateCompany(@PathVariable(name = "companyId") UUID companyId,
                                         @RequestBody final CompanyRequest request,
                                         @RequestHeader(value = "X-User-Name") String username,
                                         @RequestHeader(value = "X-User-Role") String role,
                                         @RequestHeader(value = "X-User-AffiliationId") UUID affiliationId) {

        if ("ROLE_MASTER".equals(role)) return companyService.updateCompany(companyId, request, username);
        else if ("ROLE_HUBMANAGER".equals(role)) {
            if(hubManagerService.getHubId(affiliationId).equals(request.hubId())) return companyService.updateCompany(companyId, request, username);
            else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        } else if ("ROLE_COMPANY".equals(role) && companyId.equals(affiliationId)) return companyService.updateCompany(companyId, request, username);
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
    }

    // 업체 삭제
    @DeleteMapping("/{companyId}")
    public Boolean deleteCompany(@PathVariable UUID companyId,
                                 @RequestHeader(value = "X-User-Name") String username,
                                 @RequestHeader(value = "X-User-Role") String role,
                                 @RequestHeader(value = "X-User-AffiliationId") UUID affiliationId) {

        // 마스터, 소속 허브 매니저만 권한 있음
        if ("ROLE_MASTER".equals(role)) return companyService.deleteCompany(companyId, username);
        else if ("ROLE_HUBMANAGER".equals(role)) {
            if(hubManagerService.getHubId(affiliationId).equals(companyService.findHubId(companyId))) return companyService.deleteCompany(companyId, username);
            else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        } else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
    }

    // 업체 전체 조회
    @GetMapping
    public Page<CompanyListResponse> getCompanies(@RequestHeader(value = "X-User-Role") String role,
                                                  @RequestParam(required = false) UUID companyId,
                                                  @RequestParam(required = false) UUID hubId,
                                                  @RequestParam(required = false) String name,
                                                  @RequestParam(defaultValue = "10") int page,
                                                  @RequestParam(defaultValue = "1") int size) {

        CompanySearch search = new CompanySearch(companyId, hubId, name);

        if ("ROLE_MASTER".equals(role) || "ROLE_HUBMANAGER".equals(role) || "ROLE_COMPANY".equals(role)) {
            Pageable pageable = PageRequest.of(page, size);
            return companyService.getCompanies(search, pageable);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
    }

    // 업체 상세 조회
    @GetMapping("/{companyId}")
    public CompanyResponse getCompanyById(@PathVariable UUID companyId,
                                          @RequestHeader(value = "X-User-Role") String role,
                                          @RequestHeader(value = "X-User-AffiliationId") UUID affiliationId) {

        if ("ROLE_MASTER".equals(role)) return companyService.getCompanyById(companyId);
        else if ("ROLE_HUBMANAGER".equals(role)) {
            if(hubManagerService.getHubId(affiliationId).equals(companyService.findHubId(companyId))) return companyService.getCompanyById(companyId);
            else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        } else if ("ROLE_COMPANY".equals(role) && companyId.equals(affiliationId)) return companyService.getCompanyById(companyId);
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
    }

    // 업체 ID 체크
    @GetMapping("/checkId")
    public boolean checkId(@RequestParam UUID companyId) {
        return companyService.checkId(companyId);
    }
}
