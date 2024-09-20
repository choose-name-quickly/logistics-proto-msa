package com.namequickly.logistics.order.presentation.controller;

import com.namequickly.logistics.common.response.CommonResponse;
import com.namequickly.logistics.order.application.dto.OrderCreateRequestDto;
import com.namequickly.logistics.order.application.dto.OrderCreateResponseDto;
import com.namequickly.logistics.order.application.dto.OrderDeleteResponseDto;
import com.namequickly.logistics.order.application.dto.OrderResponseDto;
import com.namequickly.logistics.order.application.dto.OrderUpdateRequestDto;
import com.namequickly.logistics.order.application.dto.OrderUpdateResponseDto;
import com.namequickly.logistics.order.application.service.OrderService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    // TODO 응답값 나중에 다시 바꾸기 (지금은 빈값으로 다 반환하게끔 만들었음)

    /**
     * 주문 생성
     *
     * @param requestDto
     * @return
     */
    @PreAuthorize("hasAnyAuthority('MASTER','HUBMANAGER','COMPANY')")
    @PostMapping("/orders")
    public CommonResponse<OrderCreateResponseDto> createOrder(
        @RequestBody OrderCreateRequestDto requestDto) {
        return CommonResponse.success(orderService.createOrder(requestDto));
    }

    /**
     * 주문 취소
     *
     * @param orderId
     * @return
     */
    @PreAuthorize("hasAnyAuthority('MASTER','HUBMANAGER','COMPANY')")
    @DeleteMapping("/orders/{orderId}")
    public CommonResponse<OrderDeleteResponseDto> cancelOrder(
        @PathVariable("orderId") UUID orderId) {

        return CommonResponse.success(orderService.cancelOrder(orderId));
    }


    /**
     * 주문 수정
     *
     * @param orderId
     * @return
     */
    @PreAuthorize("hasAnyAuthority('MASTER','HUBMANAGER') || (hasAuthority('COMPANY') && @orderAuthService.isOrderOwner(#orderId))")
    @PutMapping("/orders/{orderId}")
    public CommonResponse<OrderUpdateResponseDto> updateOrder(@PathVariable("orderId") UUID orderId,
        @RequestBody List<OrderUpdateRequestDto> requestDtos) {
        return CommonResponse.success(orderService.updateOrder(orderId, requestDtos));
    }

    /**
     * 주문 정보 단건 조회
     */
    @GetMapping("/orders/{orderId}")
    public CommonResponse<OrderResponseDto> getOrder(@PathVariable("orderId") UUID orderId,
        @RequestParam(name = "isDelete", defaultValue = "false") boolean isDelete) {
        OrderResponseDto orderResponseDto = orderService.getOrder(orderId, isDelete);
        return CommonResponse.success(orderResponseDto);
    }


    /**
     * 주문 정보 전체 조회
     */
    @GetMapping("/orders")
    public CommonResponse<Page<OrderResponseDto>> getAllOrders(
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "isAsc", defaultValue = "true") boolean isAsc,
        @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
        @RequestParam(name = "isDelete", defaultValue = "false") boolean isDelete) {

        Page<OrderResponseDto> orderResponseDtos = orderService.getAllOrders(page - 1, size, isAsc,
            sortBy, isDelete);
        return CommonResponse.success(orderResponseDtos);
    }

    /**
     * 내가 한 주문 정보 전체 조회
     */
    @PreAuthorize("hasAnyAuthority('MASTER','HUBMANAGER','COMPANY')")
    @GetMapping("/orders/mine")
    public CommonResponse<Page<OrderResponseDto>> getAllMineOrders(
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "isAsc", defaultValue = "true") boolean isAsc,
        @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
        @RequestParam(name = "isDelete", defaultValue = "false") boolean isDelete) {

        Page<OrderResponseDto> orderResponseDtos = orderService.getAllMineOrders(page - 1, size,
            isAsc,
            sortBy, isDelete);
        return CommonResponse.success(orderResponseDtos);
    }


    // 내부 서버 통신 (feign client)
    @GetMapping("/order-product/in-delivery")
    public CommonResponse<Boolean> checkProductInDelivery(
        @RequestParam("productId") UUID productId) {
        return CommonResponse.success(orderService.checkProductInDelivery(productId));
    }

}
