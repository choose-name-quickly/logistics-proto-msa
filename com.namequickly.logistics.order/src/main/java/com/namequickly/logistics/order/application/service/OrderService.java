package com.namequickly.logistics.order.application.service;


import com.namequickly.logistics.common.exception.GlobalException;
import com.namequickly.logistics.common.response.ResultCase;
import com.namequickly.logistics.common.shared.UserRole;
import com.namequickly.logistics.order.application.dto.OrderCreateRequestDto;
import com.namequickly.logistics.order.application.dto.OrderCreateRequestDto.OrderProductRequestDto;
import com.namequickly.logistics.order.application.dto.OrderCreateResponseDto;
import com.namequickly.logistics.order.application.dto.OrderDeleteResponseDto;
import com.namequickly.logistics.order.application.dto.OrderResponseDto;
import com.namequickly.logistics.order.application.dto.OrderUpdateRequestDto;
import com.namequickly.logistics.order.application.dto.OrderUpdateResponseDto;
import com.namequickly.logistics.order.application.dto.client.CompanyResponse;
import com.namequickly.logistics.order.application.dto.client.HubRouteCourierDto;
import com.namequickly.logistics.order.application.dto.client.OperationType;
import com.namequickly.logistics.order.application.dto.client.StockUpdateRequest;
import com.namequickly.logistics.order.application.mapper.OrderMapper;
import com.namequickly.logistics.order.domain.model.delivery.Delivery;
import com.namequickly.logistics.order.domain.model.delivery.DeliveryRoute;
import com.namequickly.logistics.order.domain.model.delivery.DeliveryStatus;
import com.namequickly.logistics.order.domain.model.order.Order;
import com.namequickly.logistics.order.domain.model.order.OrderProduct;
import com.namequickly.logistics.order.infrastructure.client.HubClient;
import com.namequickly.logistics.order.infrastructure.client.ProductCompanyClient;
import com.namequickly.logistics.order.infrastructure.repository.DeliveryRepository;
import com.namequickly.logistics.order.infrastructure.repository.DeliveryRouteRepository;
import com.namequickly.logistics.order.infrastructure.repository.OrderProductRepository;
import com.namequickly.logistics.order.infrastructure.repository.OrderRepository;
import com.namequickly.logistics.order.infrastructure.security.CustomUserDetails;
import com.namequickly.logistics.order.infrastructure.security.SecurityUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final FeignClientService feignClientService;

    private final OrderMapper orderMapper;
    private final HubClient hubClient;
    private final ProductCompanyClient productCompanyClient;

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryRouteRepository deliveryRouteRepository;


    /**
     * 주문 생성
     */
    @Transactional(readOnly = false)
    public OrderCreateResponseDto createOrder(OrderCreateRequestDto requestDto) {
        // TODO 리팩터링 필요한 부분
        // 권한을 위한 값을 가져오는 부분 + 유효성 체크하는 부분이 메소드 마다 반복됨
        // 분리 시킬 순 없을까

        CustomUserDetails userDetails = SecurityUtils.getCurrentUserDetails();
        String userName = userDetails.getUsername();
        String affiliationId = userDetails.getAffiliationId();
        String userRole = userDetails.getRoleAsString();

        if (!feignClientService.checkCompanyId(requestDto.getSupplierId())) {
            throw new GlobalException(ResultCase.NOT_FOUND_COMPANY);
        }

        if (!feignClientService.checkCompanyId(requestDto.getReceiverId())) {
            throw new GlobalException(ResultCase.NOT_FOUND_COMPANY);
        }

        if (feignClientService.getHub(requestDto.getOriginHubId()) == null) {
            throw new GlobalException(ResultCase.HUB_NOT_FOUND);
        }
        if (feignClientService.getHub(requestDto.getDestinationHubId()) == null) {
            throw new GlobalException(ResultCase.HUB_NOT_FOUND);
        }

        if (userRole.equals(UserRole.HUBMANAGER.getAuthority())) {
            if (!affiliationId.equals(requestDto.getOriginHubId().toString())) {
                throw new GlobalException(ResultCase.UNAUTHORIZED_HUB);
            }
        } else if (userRole.equals(UserRole.COMPANY.getAuthority())) {
            if (!affiliationId.equals(requestDto.getSupplierId().toString())) {
                throw new GlobalException(ResultCase.UNAUTHORIZED_COMPANY);
            }
        }

        // 1. 주문 생성 (Order)
        Order order = Order.create(requestDto.getSupplierId(), requestDto.getReceiverId());

        // 2. 주문 상품 생성 (OrderProducts)
        List<OrderProductRequestDto> productDtos = requestDto.getProducts();

        for (OrderProductRequestDto productDto : productDtos) {
            // TODO 이 부분 추후에 리팩터링이 필요하다고 생각
            // 하나씩 보내는 것이 아닌 List 형태로 전달하고,
            // List 형태로 전달 받는게 네트워크 비용을 줄일 수 있는 방법이라고 생각함
            productCompanyClient.updateStockQuantity(productDto.getProductId(),
                StockUpdateRequest.builder()
                    .stockQuantity(productDto.getOrderQuantity())
                    .operationType(OperationType.DECREASE)
                    .build());

            OrderProduct orderProduct = OrderProduct.create(order, productDto.getProductId(),
                productDto.getOrderQuantity());
            order.addOrderProduct(orderProduct);
        }

        // 3. 배달 생성 (Delivery)
        Delivery delivery = Delivery.create(requestDto.getOriginHubId(),
            requestDto.getDestinationHubId(), requestDto.getRecipientName(),
            requestDto.getRecipientSlackId(), requestDto.getDeliveryAddress(), order);
        order.addDelivery(delivery);

        //orderRepository.save(order);

        // 4. 배달 경유 생성(DeliveryRoutes)
        List<HubRouteCourierDto> hubRouteDtos = new ArrayList<>();

        // hub로 부터 경로 가져오기
        List<Map<UUID, UUID>> hubRouteCourierDtos = feignClientService.findOptimalRoute(
            requestDto.getOriginHubId(), requestDto.getDestinationHubId());
        
        for (Map<UUID, UUID> hubRouteCourierDto : hubRouteCourierDtos) {
            UUID routeHubId = hubRouteCourierDto.keySet().iterator().next();
            UUID courierId = hubRouteCourierDto.get(routeHubId);

            HubRouteCourierDto dto = HubRouteCourierDto.builder()
                .routeHubId(routeHubId)
                .courierId(courierId)
                .build();

            hubRouteDtos.add(dto);
        }

        for (HubRouteCourierDto hubRouteDto : hubRouteDtos) {
            DeliveryRoute deliveryRoute = DeliveryRoute.create(
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
        CustomUserDetails userDetails = SecurityUtils.getCurrentUserDetails();
        String userName = userDetails.getUsername();
        String affiliationId = userDetails.getAffiliationId();
        String userRole = userDetails.getRoleAsString();

        Order order = orderRepository.findById(orderId).orElseThrow(
            () -> new GlobalException(ResultCase.NOT_FOUND_ORDER)
        );

        CompanyResponse companyDto = feignClientService.getCompanyById(order.getSupplierId(),
            userRole, affiliationId);

        if (userRole.equals(UserRole.HUBMANAGER.getAuthority())) {
            if (!affiliationId.equals(companyDto.getHubId().toString())) {
                throw new GlobalException(ResultCase.UNAUTHORIZED_HUB);
            }
        } else if (userRole.equals(UserRole.COMPANY.getAuthority())) {
            if (!affiliationId.equals(companyDto.getCompanyId().toString())) {
                throw new GlobalException(ResultCase.UNAUTHORIZED_COMPANY);
            }
        }

        if (DeliveryStatus.HUB_WAITING.equals(order.getDeliveryStatus())) {
            order.cancelOrder(userName);
            for (OrderProduct orderProduct : order.getOrderProducts()) {

                productCompanyClient.updateStockQuantity(orderProduct.getProductId(),
                    StockUpdateRequest.builder()
                        .stockQuantity(orderProduct.getOrderQuantity())
                        .operationType(OperationType.INCREASE)
                        .build());
            }

        } else {
            throw new GlobalException(ResultCase.CANNOT_DELETE_ORDER_IN_DELIVERY);
        }

        return orderMapper.toOrderDeleteResponseDto(order);
    }

    /**
     * 주문 수정
     *
     * @param orderId
     */
    @Transactional(readOnly = false)
    public OrderUpdateResponseDto updateOrder(UUID orderId,
        List<OrderUpdateRequestDto> updateDtos) {

        CustomUserDetails userDetails = SecurityUtils.getCurrentUserDetails();
        String userName = userDetails.getUsername();
        String affiliationId = userDetails.getAffiliationId();
        String userRole = userDetails.getRoleAsString();

        Order order = orderRepository.findById(orderId).orElseThrow(
            () -> new GlobalException(ResultCase.NOT_FOUND_ORDER)
        );

        CompanyResponse companyDto = feignClientService.getCompanyById(order.getSupplierId(),
            userRole, affiliationId);

        if (userRole.equals(UserRole.HUBMANAGER.getAuthority())) {
            if (!affiliationId.equals(companyDto.getHubId().toString())) {
                throw new GlobalException(ResultCase.UNAUTHORIZED_HUB);
            }
        } else if (userRole.equals(UserRole.COMPANY.getAuthority())) {
            if (!affiliationId.equals(companyDto.getCompanyId().toString())) {
                throw new GlobalException(ResultCase.UNAUTHORIZED_COMPANY);
            }
        }

        if (!order.getDeliveryStatus().equals(DeliveryStatus.HUB_WAITING)) {
            throw new GlobalException(ResultCase.CANNOT_UPDATE_ORDER_IN_DELIVERY);
        }

        List<OrderUpdateResponseDto.OrderProductResponseDto> updatedProducts = new ArrayList<>();

        for (OrderUpdateRequestDto updateDto : updateDtos) {
            UUID productId = updateDto.getProductId();
            int requestQuantity = updateDto.getOrderQuantity();

            order.getOrderProducts().stream()
                .filter(orderProduct -> orderProduct.getProductId().equals(productId))
                .findFirst()
                .ifPresent(orderProduct -> {
                    int currentQuantity = orderProduct.getOrderQuantity();
                    int quantityDifference = requestQuantity - currentQuantity;

                    OperationType operationType =
                        (quantityDifference > 0) ? OperationType.DECREASE : OperationType.INCREASE;
                    int absoluteDifference = Math.abs(quantityDifference);

                    if (absoluteDifference > 0) {
                        productCompanyClient.updateStockQuantity(productId,
                            StockUpdateRequest.builder()
                                .stockQuantity(absoluteDifference)
                                .operationType(operationType)
                                .build());
                    }

                    orderProduct.updateOrderQuantity(requestQuantity);

                    updatedProducts.add(OrderUpdateResponseDto.OrderProductResponseDto.builder()
                        .productId(productId)
                        .orderQuantity(requestQuantity)
                        .updatedBy(order.getUpdatedAt())
                        .build());
                });
        }

        // OrderUpdateResponseDto 반환
        return OrderUpdateResponseDto.builder()
            .orderId(order.getOrderId())
            .products(updatedProducts)
            .build();
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


    public Page<OrderResponseDto> getAllMineOrders(int page, int size, boolean isAsc, String sortBy,
        boolean isDelete) {
        //정렬 방향
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        CustomUserDetails userDetails = SecurityUtils.getCurrentUserDetails();
        String userName = userDetails.getUsername();
        String affiliationId = userDetails.getAffiliationId();
        String userRole = userDetails.getRoleAsString();

        Page<Order> orders = Page.empty(pageable);

        if (userRole.equals(UserRole.HUBMANAGER.getAuthority())) {
            orders = orderRepository.findAllOrderDetailsByHubId(pageable, isDelete,
                UUID.fromString(affiliationId));
        } else if (userRole.equals(UserRole.COMPANY.getAuthority())) {
            orders = orderRepository.findAllOrderDetailsByCompanyId(pageable, isDelete,
                UUID.fromString(affiliationId));
        }

        return orders.map(orderMapper::toOrderResponseDto);
    }


    // feign Client 통신
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
