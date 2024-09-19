package com.namequickly.logistics.order.application.service;

import com.namequickly.logistics.order.domain.model.order.Order;
import com.namequickly.logistics.order.infrastructure.repository.OrderRepository;
import com.namequickly.logistics.order.infrastructure.security.SecurityUtils;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderAuthService {

    private final OrderRepository orderRepository;

    public boolean isOrderOwner(UUID orderId) {
        String userName = SecurityUtils.getCurrentUserDetails().getUsername();
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return userName.equals(order.getCreatedBy());
    }
}
