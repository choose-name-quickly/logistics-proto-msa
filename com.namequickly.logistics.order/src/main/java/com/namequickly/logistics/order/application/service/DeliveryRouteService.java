package com.namequickly.logistics.order.application.service;

import com.namequickly.logistics.order.infrastructure.repository.DeliveryRepository;
import com.namequickly.logistics.order.infrastructure.repository.DeliveryRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryRouteService {

    // TODO 우선은 이정도만..
    private final DeliveryRouteRepository deliveryRouteRepository;
    private final DeliveryRepository deliveryRepository;


}
