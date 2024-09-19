package com.namequickly.logistics.hub_management.application.service;

import com.namequickly.logistics.hub_management.application.dto.CompanyListResponse;
import com.namequickly.logistics.hub_management.application.dto.CompanyResponse;
import com.namequickly.logistics.hub_management.domain.model.company.Company;
import com.namequickly.logistics.hub_management.domain.repository.company.CompanyRepo;
import com.namequickly.logistics.hub_management.presentation.dto.company.CompanyRequest;
import com.namequickly.logistics.hub_management.presentation.dto.company.CompanySearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class CompanyService {

    private final CompanyRepo companyRepo;


    public CompanyService(CompanyRepo companyRepo) {
        this.companyRepo = companyRepo;
    }

    // 업체 등록
    @Transactional
    public CompanyResponse createCompany(CompanyRequest request, String username) {
            Company company = Company.create(request, username);
            companyRepo.save(company);
            return CompanyResponse.toResponse(company);
    }

    // 업체 수정
    @Transactional
    public CompanyResponse updateCompany(UUID companyId, CompanyRequest request, String username) {
        Company company = companyRepo.findById(companyId)
                .filter(p -> !p.getIsDelete())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 정보를 찾을 수 없거나 이미 삭제된 상태입니다."));

        company.update(request, username);
        return CompanyResponse.toResponse(companyRepo.save(company));
    }

    // TODO: userId 검증
    // 업체 삭제
    @Transactional
    public Boolean deleteCompany(UUID companyId,  String username) {
        try {
            Company company = companyRepo.findById(companyId)
                    .filter(p -> !p.getIsDelete())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 정보를 찾을 수 없거나 이미 삭제된 상태입니다."));
            companyRepo.delete(company);
            return true;
        }  catch (Exception e) { return false; }
    }

    // 업체 전체 조회
    public Page<CompanyListResponse> getCompanies(CompanySearch search, Pageable pageable) {
        return companyRepo.searchCompanies(search, pageable);
    }

    // 업체 상세 조회
    // 지연 로딩
    @Transactional(readOnly = true)
    public CompanyResponse getCompanyById(UUID companyId) {
        Company company = companyRepo.findById(companyId)
                .filter(p -> !p.getIsDelete())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 정보를 찾을 수 없거나 이미 삭제된 상태입니다."));
        return CompanyResponse.toResponse(company);
    }

    // 업체 ID 체크
    @Transactional
    public boolean checkId(UUID companyId) {
        return companyRepo.checkId(companyId) != null;
    }

    // 업체 소속 허브 ID
    @Transactional
    public UUID findHubId(UUID companyId) {
        return companyRepo.findHubId(companyId);
    }
}
