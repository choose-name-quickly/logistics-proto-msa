package com.namequickly.logistics.product_company.application.service;


import com.namequickly.logistics.common.exception.GlobalException;
import com.namequickly.logistics.common.response.ResultCase;
import com.namequickly.logistics.product_company.application.dto.client.CompanyResponse;
import com.namequickly.logistics.product_company.application.dto.client.HubResponseDto;
import com.namequickly.logistics.product_company.infrastructure.client.CompanyClient;
import com.namequickly.logistics.product_company.infrastructure.client.HubClient;
import com.namequickly.logistics.product_company.infrastructure.client.OrderClient;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeignClientService {

    private final CompanyClient companyClient;
    private final HubClient hubClient;
    private final OrderClient orderClient;


    // TODO 나중에 feign client 개발 완료되면 주석 풀기
    public CompanyResponse getCompanyById(UUID companyId, String userRole) {
        return companyClient.getCompanyById(companyId, userRole);
    }

    public HubResponseDto getHub(UUID hubId) {
        return hubClient.getHub(hubId).getData();
    }

    public void checkProductInDelivery(UUID productId) {
        if (orderClient.checkProductInDelivery(productId).getData()) {
            throw new GlobalException(ResultCase.CANNOT_DELETE_PRODUCT_IN_DELIVERY);
        }
    }

}
