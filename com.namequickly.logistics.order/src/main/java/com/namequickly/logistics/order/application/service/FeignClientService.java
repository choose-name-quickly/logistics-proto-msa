package com.namequickly.logistics.order.application.service;

import com.namequickly.logistics.order.application.dto.client.CompanyResponse;
import com.namequickly.logistics.order.application.dto.client.HubResponseDto;
import com.namequickly.logistics.order.application.dto.client.HubRouteCourierDto;
import com.namequickly.logistics.order.application.dto.client.RouteHubResponseDto;
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
    public CompanyResponse getCompanyById(UUID companyId, String userRole, String affiliationId) {
        return companyClient.getCompanyById(companyId, userRole, UUID.fromString(affiliationId));
    }

    public HubResponseDto getHub(UUID hubId) {
        return hubClient.getHub(hubId).getData();
    }


    public RouteHubResponseDto getHubRoute(UUID hubId) {
        return hubClient.getRouteHub(hubId).getData();
    }

/*    public List<RouteHubResponseDto> getHubRoutes(UUID routeId) {
        return hubClient.getHubRoutes(routeId);
    }*/

    public List<HubRouteCourierDto> getHubRoutes(UUID originHubId, UUID destinationHubId) {
        return hubClient.getHubRoutes(originHubId, destinationHubId);
    }


    public boolean checkCompanyId(UUID companyId) {
        return companyClient.checkId(companyId);
    }
}
