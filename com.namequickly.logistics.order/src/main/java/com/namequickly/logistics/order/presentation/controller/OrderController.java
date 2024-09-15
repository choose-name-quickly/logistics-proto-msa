package com.namequickly.logistics.order.presentation.controller;

import com.namequickly.logistics.common.response.CommonResponse;
import com.namequickly.logistics.common.response.CommonResponse.CommonEmptyRes;
import com.namequickly.logistics.order.application.dto.OrderResponseDto;
import com.namequickly.logistics.order.application.service.OrderService;
import com.namequickly.logistics.order.presentation.dto.OrderCreateRequestDto;
import com.namequickly.logistics.order.presentation.dto.OrderUpdateRequestDto;
import com.namequickly.logistics.order.presentation.mapper.OrderMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    @PostMapping("/orders")
    public CommonResponse<CommonEmptyRes> createOrder(
        @RequestBody OrderCreateRequestDto requestDto) {
        orderService.createOrder(OrderMapper.INSTANCE.toOrderCreateDto(requestDto));
        return CommonResponse.success();
    }

    /**
     * 주문 취소
     *
     * @param orderId
     * @return
     */
    @DeleteMapping("/orders/{orderId}")
    public CommonResponse<CommonEmptyRes> cancelOrder(@PathVariable("orderId") UUID orderId) {
        orderService.cancelOrder(orderId);
        return CommonResponse.success();
    }


    /**
     * 주문 수정
     *
     * @param orderId
     * @return
     */
    @PutMapping("/orders/{orderId}")
    public CommonResponse<CommonEmptyRes> updateOrder(@PathVariable("orderId") UUID orderId,
        @RequestBody OrderUpdateRequestDto requestDto) {
        orderService.updateOrder(orderId, OrderMapper.INSTANCE.toOrderUpdateDto(requestDto));
        return CommonResponse.success();
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


    // 내부 서버 통신 (feign client)
    @GetMapping("/order-product/in-delivery")
    public CommonResponse<Boolean> checkProductInDelivery(
        @RequestParam("productId") UUID productId) {
        System.out.println("오긴 왓니?");
        return CommonResponse.success(orderService.checkProductInDelivery(productId));
    }

}
