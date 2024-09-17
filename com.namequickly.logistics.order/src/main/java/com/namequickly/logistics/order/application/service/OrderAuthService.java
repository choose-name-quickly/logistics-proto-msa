package com.namequickly.logistics.order.application.service;

import com.namequickly.logistics.order.domain.model.order.Order;
import com.namequickly.logistics.order.infrastructure.repository.OrderRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderAuthService {

    private final OrderRepository orderRepository;

    public boolean isOrderOwner(UUID orderId) {
        String userName = (String) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return userName.equals(order.getCreatedBy());
    }
}
