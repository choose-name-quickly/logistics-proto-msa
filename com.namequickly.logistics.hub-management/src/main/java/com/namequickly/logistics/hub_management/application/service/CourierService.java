package com.namequickly.logistics.hub_management.application.service;

import com.namequickly.logistics.hub_management.application.dto.CourierListResponse;
import com.namequickly.logistics.hub_management.application.dto.CourierResponse;
import com.namequickly.logistics.hub_management.domain.model.courier.Courier;
import com.namequickly.logistics.hub_management.domain.model.courier.CourierStatus;
import com.namequickly.logistics.hub_management.domain.repository.courier.CourierRepo;
import com.namequickly.logistics.hub_management.presentation.dto.courier.CourierRequest;
import com.namequickly.logistics.hub_management.presentation.dto.courier.CourierSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class CourierService {

    private final CourierRepo courierRepo;

    public CourierService(CourierRepo courierRepo) {
        this.courierRepo = courierRepo;;
    }

    // 배송 기사 등록
    @Transactional
    public CourierResponse createCourier(CourierRequest request, String username) {
            Courier courier = Courier.create(request, username);
            return CourierResponse.toResponse(courierRepo.save(courier));
    }

    // 배송 기사 수정
    @Transactional
    public CourierResponse updateCourier(UUID courierId, CourierRequest request, String username) {
        Courier courier = courierRepo.findById(courierId)
                .filter(p -> !p.getIsDelete())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 정보를 찾을 수 없거나 이미 삭제된 상태입니다."));

        courier.update(request, username);
        return CourierResponse.toResponse(courier);
    }

    // 배송 기사 삭제
    @Transactional
    public Boolean deleteCourier(UUID courierId, String username) {
        try {
            Courier courier = courierRepo.findById(courierId)
                    .filter(p -> !p.getIsDelete())
                    .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 정보를 찾을 수 없거나 이미 삭제된 상태입니다."));

            courier.delete(username);
            return true;
        } catch (Exception e) { return false; }
    }

    // 배송 기사 전체 조회
    public Page<CourierListResponse> getCouriers(CourierSearch search, Pageable pageable) {
        return courierRepo.searchCouriers(search, pageable);
    }

    // 배송 기사 상세 조회
    @Transactional(readOnly = true)
    public CourierResponse getCourierById(UUID courierId) {
        Courier courier = courierRepo.findById(courierId)
                .filter(p -> !p.getIsDelete())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 정보를 찾을 수 없거나 이미 삭제된 상태입니다."));

        return CourierResponse.toResponse(courier);
    }

    // 배송기사 배정
    public List<Map<UUID, UUID>> assignCouriers(List<UUID> routeHubList) {
        // 배정이 필요한 경로아이디를 가져옴.
        // 대기 중인 상태의 기사 목록을 추출
            List<UUID> courierList = courierRepo.findCourierIdsByStatus(CourierStatus.AVAILABLE);
            // 랜덤으로 기사 배치
            Collections.shuffle(courierList);
        // 매치해준다.
            // TODO: 기사
            List<Map<UUID, UUID>> mapList = new ArrayList<>();
            // 기사 배정 요청이 실제 배정이 가능한 수보다 많을 경우를 대비
            int size = Math.min(routeHubList.size(), courierList.size());

            for(int i=0; i<size; i++) {
                Map<UUID, UUID> map = new HashMap<>();
                map.put(routeHubList.get(i), courierList.get(i));
                mapList.add(map);
            }
        return mapList;
    }

    // 배송기사 ID 체크
    @Transactional
    public boolean checkId(UUID courierId) { return courierRepo.checkId(courierId) != null;}

    // 배송기사 소속 허브 ID
    @Transactional
    public UUID findHubId(UUID courierId) { return courierRepo.findHubId(courierId); }
}
