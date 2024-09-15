package com.namequickly.logistics.order.application.service;


import com.namequickly.logistics.order.application.dto.HubRouteDto;
import com.namequickly.logistics.order.application.dto.OrderCreateDto;
import com.namequickly.logistics.order.application.dto.OrderCreateDto.OrderProductDto;
import com.namequickly.logistics.order.application.dto.OrderResponseDto;
import com.namequickly.logistics.order.application.dto.OrderUpdateDto;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final HubClient hubClient;

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryRouteRepository deliveryRouteRepository;


    /**
     * 주문 생성
     */
    @Transactional(readOnly = false)
    public void createOrder(OrderCreateDto createDto) {
        // 1. 주문 생성 (Order)
        Order order = Order.create(createDto.getSupplierId(), createDto.getReceiverId());

        // 2. 주문 상품 생성 (OrderProducts)
        List<OrderProductDto> productDtos = createDto.getProducts();

        for (OrderProductDto productDto : productDtos) {
            OrderProduct orderProduct = OrderProduct.create(order, productDto.getProductId(),
                productDto.getOrderQuantity());
            order.addOrderProduct(orderProduct);
        }

        // 3. 배달 생성 (Delivery)
        Delivery delivery = Delivery.create(createDto.getOriginHubId(),
            createDto.getDestinationHubId(), createDto.getRecipientName(),
            createDto.getRecipientSlackId(), order);
        order.addDelivery(delivery);

        //orderRepository.save(order);

        // 4. 배달 경유 생성(DeliveryRoutes)
        // TODO 나중에 Hub 개발 완료되면 주석 제거
        //List<HubRouteDto> hubRouteDtos = hubClient.getHubRoutes(createDto.getOriginHubId(),
        //    createDto.getDestinationHubId());

        // TODO 임시데이터 , 나중에 Hub 개발 완료되면 로직 삭제
        List<HubRouteDto> hubRouteDtos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            hubRouteDtos.add(HubRouteDto.builder()
                .routeHubId(UUID.randomUUID())
                .courierId(UUID.randomUUID())
                .sequence(i + 1)
                .build());
        }

        for (HubRouteDto hubRouteDto : hubRouteDtos) {
            DeliveryRoute deliveryRoute = DeliveryRoute.create(hubRouteDto.getSequence(),
                hubRouteDto.getRouteHubId(),
                hubRouteDto.getCourierId(), delivery);
            delivery.addDeliveryRoute(deliveryRoute);
        }
        orderRepository.save(order);
    }

    /**
     * 주문 취소
     *
     * @param orderId
     */
    @Transactional(readOnly = false)
    public void cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
            () -> new IllegalArgumentException("Order not found")
        );

        String userName = (String) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();

        if (DeliveryStatus.HUB_WAITING.equals(order.getDeliveryStatus())) {
            order.cancelOrder(userName);
        } else {
            throw new IllegalArgumentException("Order not cancelled");
        }
    }

    /**
     * 주문 수정
     *
     * @param orderId
     */
    @PreAuthorize("hasAnyRole('MASTER','HUB') || (hasRole('COMPANY') && @orderAuthService.isOrderOwner(#orderId))")
    @Transactional(readOnly = false)
    public void updateOrder(UUID orderId, OrderUpdateDto updateDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(
            () -> new IllegalArgumentException("Order not found")
        );

        if (order.getDeliveryStatus().equals(DeliveryStatus.HUB_WAITING)) {
            order.updateOrderQuantity(updateDto.getProductId(), updateDto.getOrderQuantity());
        }
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
        return OrderMapper.INSTANCE.toOrderResponseDto(order);
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

        return orders.map(OrderMapper.INSTANCE::toOrderResponseDto);

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
