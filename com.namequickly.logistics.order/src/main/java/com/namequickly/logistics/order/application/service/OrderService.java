package com.namequickly.logistics.order.application.service;


import com.namequickly.logistics.common.exception.GlobalException;
import com.namequickly.logistics.common.response.ResultCase;
import com.namequickly.logistics.order.application.dto.HubRouteDto;
import com.namequickly.logistics.order.application.dto.OrderCreateRequestDto;
import com.namequickly.logistics.order.application.dto.OrderCreateRequestDto.OrderProductRequestDto;
import com.namequickly.logistics.order.application.dto.OrderCreateResponseDto;
import com.namequickly.logistics.order.application.dto.OrderDeleteResponseDto;
import com.namequickly.logistics.order.application.dto.OrderResponseDto;
import com.namequickly.logistics.order.application.dto.OrderUpdateRequestDto;
import com.namequickly.logistics.order.application.dto.OrderUpdateResponseDto;
import com.namequickly.logistics.order.application.mapper.OrderMapper;
import com.namequickly.logistics.order.domain.model.delivery.Delivery;
import com.namequickly.logistics.order.domain.model.delivery.DeliveryRoute;
import com.namequickly.logistics.order.domain.model.delivery.DeliveryStatus;
import com.namequickly.logistics.order.domain.model.order.Order;
import com.namequickly.logistics.order.domain.model.order.OrderProduct;
import com.namequickly.logistics.order.infrastructure.client.HubClient;
import com.namequickly.logistics.order.infrastructure.repository.DeliveryRepository;
import com.namequickly.logistics.order.infrastructure.repository.DeliveryRouteRepository;
import com.namequickly.logistics.order.infrastructure.repository.OrderProductRepository;
import com.namequickly.logistics.order.infrastructure.repository.OrderRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final VerifierService verifierService;

    private final OrderMapper orderMapper;
    private final HubClient hubClient;

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryRouteRepository deliveryRouteRepository;


    /**
     * 주문 생성
     */
    @Transactional(readOnly = false)
    public OrderCreateResponseDto createOrder(OrderCreateRequestDto requestDto) {

        // TODO 나중에 feign client 개발 완료되면 주석 풀기
        /*
        verifierService.checkCompanyExists(requestDto.getSupplierId());
        verifierService.checkCompanyExists(requestDto.getReceiverId());
        verifierService.checkHubExists(requestDto.getOriginHubId());
        verifierService.checkHubExists(requestDto.getDestinationHubId());

        // TODO 상품이 존재하는지 확인하는 것도 개발 필요 requestDto.getProductId();
        if (userRole.equals("ROLE_HUBMANAGER")) {
           // TODO 원래는 생산 업체의 허브랑 클라이언트 허브랑 같은지도 체크해야 하는데.. 아 ㅠ
            verifierService.isMatchHub(requestDto.getHubId(), userName);
        } else if (userRole.equals("ROLE_COMPANY")) {
            verifierService.isMatchCompany(requestDto.getSupplierId(), userName);
        }
        */

        // 1. 주문 생성 (Order)
        Order order = Order.create(requestDto.getSupplierId(), requestDto.getReceiverId());

        // 2. 주문 상품 생성 (OrderProducts)
        List<OrderProductRequestDto> productDtos = requestDto.getProducts();

        for (OrderProductRequestDto productDto : productDtos) {
            OrderProduct orderProduct = OrderProduct.create(order, productDto.getProductId(),
                productDto.getOrderQuantity());
            order.addOrderProduct(orderProduct);
        }
        // TODO 주문 상품 생성 될 때 재고 수량 변경되어야 할 필요가 있음
        // TODO 재고 수량 음수 안되게 조심 즉, 재고 수량보다 주문 수량이 많은지 먼저 확인해야함

        // 3. 배달 생성 (Delivery)
        Delivery delivery = Delivery.create(requestDto.getOriginHubId(),
            requestDto.getDestinationHubId(), requestDto.getRecipientName(),
            requestDto.getRecipientSlackId(), order);
        order.addDelivery(delivery);

        //orderRepository.save(order);

        // 4. 배달 경유 생성(DeliveryRoutes)
        // TODO 나중에 Hub 개발 완료되면 주석 제거
        //List<HubRouteDto> hubRouteDtos = hubClient.getHubRoutes(requestDto.getOriginHubId(),
        //    requestDto.getDestinationHubId());

        // TODO 임시데이터 , 나중에 Hub 개발 완료되면 로직 삭제
        List<HubRouteDto> hubRouteDtos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            hubRouteDtos.add(HubRouteDto.builder()
                .routeHubId(UUID.randomUUID())
                .courierId(UUID.randomUUID())
                //.sequence(i + 1)
                .build());
        }

        for (HubRouteDto hubRouteDto : hubRouteDtos) {
            DeliveryRoute deliveryRoute = DeliveryRoute.create(//hubRouteDto.getSequence(),
                hubRouteDto.getRouteHubId(),
                hubRouteDto.getCourierId(), delivery);
            delivery.addDeliveryRoute(deliveryRoute);
        }
        orderRepository.save(order);
        return orderMapper.toOrderCreateResponseDto(order);
    }

    /**
     * 주문 취소
     *
     * @param orderId
     */
    @Transactional(readOnly = false)
    public OrderDeleteResponseDto cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
            () -> new GlobalException(ResultCase.NOT_FOUND_ORDER)
        );

        String userName = (String) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();

        if (DeliveryStatus.HUB_WAITING.equals(order.getDeliveryStatus())) {
            order.cancelOrder(userName);
        } else {
            throw new GlobalException(ResultCase.CANNOT_DELETE_ORDER_IN_DELIVERY);
        }

        // TODO 주문 취소되면 재고 수량이 증가해야함

        return orderMapper.toOrderDeleteResponseDto(order);
    }

    /**
     * 주문 수정
     *
     * @param orderId
     */
    @Transactional(readOnly = false)
    public OrderUpdateResponseDto updateOrder(UUID orderId, OrderUpdateRequestDto updateDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(
            () -> new GlobalException(ResultCase.NOT_FOUND_ORDER)
        );

        if (!order.getDeliveryStatus().equals(DeliveryStatus.HUB_WAITING)) {
            throw new GlobalException(ResultCase.CANNOT_UPDATE_ORDER_IN_DELIVERY);
        }

        OrderProduct orderProduct = order.updateOrderQuantity(
            updateDto.getProductId(), updateDto.getOrderQuantity()).orElseThrow(
            () -> new GlobalException(ResultCase.NOT_FOUND_PRODUCT)
        );

        // TODO 주문 상품 수량이 변경되면 재고 상품 수량도 변경되어야 함
        
        return orderMapper.toOrderUpdateResponseDto(order, orderProduct);
    }

    /**
     * 주문 단건 조회
     *
     * @param orderId
     * @param isDelete
     * @return
     */
    public OrderResponseDto getOrder(UUID orderId, boolean isDelete) {
        Order order = orderRepository.findOrderDetails(orderId, isDelete)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // 변환 로직
        return orderMapper.toOrderResponseDto(order);
    }

    /**
     * 주문 전체 조회
     *
     * @param page
     * @param size
     * @param isAsc
     * @param sortBy
     * @param isDelete
     * @return
     */
    public Page<OrderResponseDto> getAllOrders(int page, int size, boolean isAsc, String sortBy,
        boolean isDelete) {
        //정렬 방향
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Order> orders = orderRepository.findAllOrderDetails(pageable, isDelete);

        return orders.map(orderMapper::toOrderResponseDto);

    }


    // 내부 통신
    public boolean checkProductInDelivery(UUID productId) {
        List<OrderProduct> orderProducts = orderProductRepository.findAllByproductId(productId);

        for (OrderProduct product : orderProducts) {
            if (!DeliveryStatus.DELIVERED.equals(product.getOrder().getDeliveryStatus())) {
                return true;
            }
        }

        return false;

    }
}
