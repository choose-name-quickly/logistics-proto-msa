package com.namequickly.logistics.hub_management.presentation.controller;


import com.namequickly.logistics.hub_management.application.dto.CourierListResponse;
import com.namequickly.logistics.hub_management.application.dto.CourierResponse;
import com.namequickly.logistics.hub_management.application.exception.ErrorResponse;
import com.namequickly.logistics.hub_management.application.exception.HubNotFoundException;
import com.namequickly.logistics.hub_management.application.service.CourierService;
import com.namequickly.logistics.hub_management.application.service.HubManagerService;
import com.namequickly.logistics.hub_management.domain.model.courier.CourierStatus;
import com.namequickly.logistics.hub_management.presentation.dto.courier.CourierRequest;
import com.namequickly.logistics.hub_management.presentation.dto.courier.CourierSearch;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/couriers")
public class CourierController {

    private final CourierService courierService;
    private final HubManagerService hubManagerService;

    public CourierController(CourierService courierService, HubManagerService hubManagerService) {
        this.courierService = courierService;
        this.hubManagerService = hubManagerService;
    }


    // 배송기사 등록
    @PostMapping
    public ResponseEntity<CourierResponse> addCourier(@RequestBody final CourierRequest request,
        @RequestHeader(value = "X-User-Name") String username,
        @RequestHeader(value = "X-User-Role") String role,
        @RequestHeader(value = "X-User-AffiliationId", required = false) UUID affiliationId) {
        // 마스터
        if ("MASTER".equals(role)) {
            return ResponseEntity.ok(courierService.createCourier(request, username));
        } else if ("HUBMANAGER".equals(role)) {
            // 같은 소속 허브 매니저만
            if (hubManagerService.getHubId(affiliationId).equals(request.hubId())) {
                return ResponseEntity.ok(courierService.createCourier(request, username));
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
            }
        } else {
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

    // 배송기사 수정
    @PatchMapping("/{courierId}")
    public CourierResponse updateCourier(@PathVariable UUID courierId,
        @RequestBody final CourierRequest request,
        @RequestHeader(value = "X-User-Name") String username,
        @RequestHeader(value = "X-User-Role") String role,
        @RequestHeader(value = "X-User-AffiliationId", required = false) UUID affiliationId) {

        if ("MASTER".equals(role)) {
            return courierService.updateCourier(courierId, request, username);
        } else if ("HUBMANAGER".equals(role)) {
            if (hubManagerService.getHubId(affiliationId).equals(request.hubId())) {
                return courierService.updateCourier(courierId, request, username);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
            }
        } else if ("COURIER".equals(role) && courierId.equals(affiliationId)) {
            return courierService.updateCourier(courierId, request, username);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
    }

    // 배송기사 삭제
    @DeleteMapping("/{courierId}")
    public Boolean deleteCourier(@PathVariable UUID courierId,
        @RequestHeader(value = "X-User-Name", required = true) String username,
        @RequestHeader(value = "X-User-Role", required = true) String role,
        @RequestHeader(value = "X-User-AffiliationId", required = false) UUID affiliationId) {

        // 마스터, 소속 허브 매니저만 권한 있음
        if ("MASTER".equals(role)) {
            return courierService.deleteCourier(courierId, username);
        } else if ("HUBMANAGER".equals(role)) {
            if (hubManagerService.getHubId(affiliationId)
                .equals(courierService.findHubId(courierId))) {
                return courierService.deleteCourier(courierId, username);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
    }

    // 배송기사 전체 조회
    @GetMapping
    public Page<CourierListResponse> getCouriers(
        @RequestHeader(value = "X-User-Role", required = true) String role,
        @RequestHeader(value = "X-User-AffiliationId", required = false) UUID affiliationId,
        @RequestParam(required = false) UUID courierId,
        @RequestParam(required = false) UUID hubId,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) CourierStatus status,
        @RequestParam("page") int page,
        @RequestParam("size") int size) {

        CourierSearch search = new CourierSearch(courierId, hubId, name, status);
        Pageable pageable = PageRequest.of(page, size);

        if ("MASTER".equals(role)) {
            return courierService.getCouriers(search, pageable);
        } else if ("HUBMANAGER".equals(role)) {
            if (hubManagerService.getHubId(affiliationId).equals(search.hubId())) {
                return courierService.getCouriers(search, pageable);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }

    }

    // 배송기사 상세 조회
    @GetMapping("/{courierId}")
    public CourierResponse getCourierById(@PathVariable UUID courierId,
        @RequestHeader(value = "X-User-Role", required = true) String role,
        @RequestHeader(value = "X-User-AffiliationId", required = false) UUID affiliationId) {

        if ("MASTER".equals(role)) {
            return courierService.getCourierById(courierId);
        } else if ("HUBMANAGER".equals(role)) {
            if (hubManagerService.getHubId(affiliationId)
                .equals(courierService.findHubId(courierId))) {
                return courierService.getCourierById(courierId);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
            }
        } else if ("COURIER".equals(role) && courierId.equals(affiliationId)) {
            return courierService.getCourierById(courierId);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
    }

    // 배송기사 배정
    @PostMapping("/assignCouriers")
    public List<Map<UUID, UUID>> assignCouriers(@RequestBody List<UUID> routeHubList) {
        return courierService.assignCouriers(routeHubList);
    }

    // 배송 기사 아이디
    @GetMapping("/checkId")
    public boolean checkId(@RequestParam UUID courierId) {
        return courierService.checkId(courierId);
    }
}
