package com.namequickly.logistics.hub_management.presentation.controller;


import com.namequickly.logistics.hub_management.application.dto.CourierResponse;
import com.namequickly.logistics.hub_management.application.exception.ErrorResponse;
import com.namequickly.logistics.hub_management.application.exception.HubNotFoundException;
import com.namequickly.logistics.hub_management.application.service.CourierService;
import com.namequickly.logistics.hub_management.presentation.dto.courier.CourierRequest;
import com.namequickly.logistics.hub_management.presentation.dto.courier.CourierSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/couriers")
public class CourierController {

    private final CourierService courierService;

    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    // TODO : USER 서비스에서 userId와 affiliationId, hubId 리턴해주는 메서드 필요
    // 배송기사 등록
    @PostMapping
    public ResponseEntity<CourierResponse> addCourier(@RequestBody final CourierRequest request,
                                                      @RequestHeader(value = "X-User-Id") UUID userId,
                                                      @RequestHeader(value = "X-Role") String role,
                                                      @RequestHeader(value = "X-Affiliation-Id") UUID affiliationId,
                                                      @RequestHeader(value = "X-Hub-Id") UUID hubId) {
        // 마스터, 담당 허브 매니저
        if("ROLE_MASTER".equals(role)) {
            return ResponseEntity.ok(courierService.createCourier(request, userId));
        } else if("ROLE_HUB_MANAGER".equals(role)  && (request.hubId()).equals(hubId)) {
            return ResponseEntity.ok(courierService.createCourier(request, userId));
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
                                         @RequestHeader(value = "X-User-Id") UUID userId,
                                         @RequestHeader(value = "X-Role") String role,
                                         @RequestHeader(value = "X-Affiliation-Id") UUID affiliationId,
                                         @RequestHeader(value = "X-Hub-Id") UUID hubId) {

        if ("ROLE_MASTER".equals(role)) {
            return courierService.updateCourier(courierId, request, userId);
        } else if ("ROLE_HUB_MANAGER".equals(role) && (request.hubId()).equals(hubId)) {
            return courierService.updateCourier(courierId, request, userId);
        }  else if ("ROLE_COURIER".equals(role) && courierId.equals(affiliationId)
        ) {
            return courierService.updateCourier(courierId, request, userId);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
    }

    // 배송기사 삭제
    @DeleteMapping("/{courierId}")
    public Boolean deleteCourier(@PathVariable UUID courierId,
                                 @RequestHeader(value = "X-User-Id", required = true) UUID userId,
                                 @RequestHeader(value = "X-Role", required = true) String role) {

        // 마스터, 소속 허브 매니저만 권한 있음
        if ("ROLE_MASTER".equals(role)) {
            return courierService.deleteCourier(courierId, userId);
        } // TODO: 삭제의 경우 request로 hubId를 가져오지 않아서
    // 레포지토리에서 해당 소속 id를 가져와 체크해야한다는 번거로움.
    // 서비스에서 체크해야하나? 고민해봐야함.&& (request.hubId()).equals(hubId))
        else if ("ROLE_HUB_MANAGER".equals(role)) {
            return courierService.deleteCourier(courierId, userId);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
    }

    // 배송기사 전체 조회
    @GetMapping
    public Page<CourierResponse> getCouriers(@RequestHeader(value = "X-Role", required = true) String role,
                                             @RequestBody CourierSearch search,
                                             @RequestParam("page") int page,
                                             @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);

        if ("ROLE_MASTER".equals(role)) {
            return courierService.getCouriers(search, pageable);
        } else if ("ROLE_HUB_MANAGER".equals(role) //&&
        ) {
            return courierService.getCouriers(search, pageable);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
    }

    // 배송기사 상세 조회
    @GetMapping("/{courierId}")
    public CourierResponse getCourierById(@PathVariable UUID courierId,
                                          @RequestHeader(value = "X-Role", required = true) String role,
                                          @RequestHeader(value = "X-Affiliation-Id") UUID affiliationId) {
        if ("ROLE_MASTER".equals(role)) {
            return courierService.getCourierById(courierId);
        } // TODO: 삭제의 경우 request로 hubId를 가져오지 않아서
        // 레포지토리에서 해당 소속 id를 가져와 체크해야한다는 번거로움.
        // 서비스에서 체크해야하나? 고민해봐야함.&& (request.hubId()).equals(hubId))
        else if ("ROLE_HUB_MANAGER".equals(role) ) {
            return courierService.getCourierById(courierId);
        }  else if ("ROLE_COURIER".equals(role) && courierId.equals(affiliationId)) {
            return courierService.getCourierById(courierId);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
    }

    // 배송기사 배정
    @GetMapping("/assignCouriers")
    public List<Map<UUID, UUID>> assignCouriers() {
        return courierService.assignCouriers();
    }

    // 배송 기사 아이디
    @GetMapping("/affiliationIds")
    public List<UUID> affiliationIds() {
        return courierService.getCourierIds();
    }

}
