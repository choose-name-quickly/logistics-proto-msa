package com.namequickly.logistics.order.application.service;

import com.namequickly.logistics.order.application.dto.client.CompanyDto;
import com.namequickly.logistics.order.application.dto.client.HubDto;
import com.namequickly.logistics.order.application.dto.client.HubRouteCourierDto;
import com.namequickly.logistics.order.application.dto.client.HubRouteDto;
import com.namequickly.logistics.order.infrastructure.client.CompanyClient;
import com.namequickly.logistics.order.infrastructure.client.HubClient;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeignClientService {

    private final CompanyClient companyClient;
    private final HubClient hubClient;


    // TODO 나중에 feign client 개발 완료되면 주석 풀기
    public CompanyDto getCompany(UUID companyId) {
        return companyClient.getCompany(companyId);
    }

    public HubDto getHub(UUID hubId) {
        return hubClient.getHub(hubId);
    }


    public HubRouteDto getHubRoute(UUID hubId) {
        return hubClient.getHubRoute(hubId);
    }

    public List<HubRouteDto> getHubRoutes(UUID routeId) {
        return hubClient.getHubRoutes(routeId);
    }

    public List<HubRouteCourierDto> getHubRoutes(UUID originHubId, UUID destinationHubId) {
        return hubClient.getHubRoutes(originHubId, destinationHubId);
    }


}
