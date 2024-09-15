package com.namequickly.logistics.product_company.application.service;

import com.namequickly.logistics.common.exception.GlobalException;
import com.namequickly.logistics.common.response.ResultCase;
import com.namequickly.logistics.product_company.infrastructure.client.CompanyClient;
import com.namequickly.logistics.product_company.infrastructure.client.HubClient;
import com.namequickly.logistics.product_company.infrastructure.client.OrderClient;
import com.namequickly.logistics.product_company.infrastructure.client.UserClient;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VerifierService {

    private final CompanyClient companyClient;
    private final HubClient hubClient;
    private final UserClient userClient;
    private final OrderClient orderClient;


    // TODO 나중에 feign client 개발 완료되면 주석 풀기
/*

    public void checkCompanyExists(UUID supplierId){
        if(!companyClient.checkCompanyExists(supplierId)){
            throw new GlobalException(ResultCase.NOT_FOUND_COMPANY);
        }
    }

    public void checkHubExists(UUID hubId){
        if(!hubClient.checkHubExists(hubId)){
            throw new GlobalException(ResultCase.NOT_FOUND_HUB);
        }
    }

*/
    public void checkProductInDelivery(UUID productId) {
        if (orderClient.checkProductInDelivery(productId).getData()) {
            throw new GlobalException(ResultCase.CANNOT_DELETE_PRODUCT_IN_DELIVERY);
        }
    }
/*

    public boolean isMatchHub(UUID hubId, userName){
        UserInfoDto userInfoDto = userClient.findUser(userName);
        if(!userInfoDto.getAffiliationId().equals(hubId)){
            throw new GlobalException(ResultCase.UNAUTHORIZED_HUB);
        }else{
            return true;
        }
    }


    public boolean isMatchCompany(UUID supplierId, userName){
        UserInfoDto userInfoDto = userClient.findUser(userName);
        if(!userInfoDto.getAffiliationId().equals(supplierId)){
            throw new GlobalException(ResultCase.UNAUTHORIZED_COMPANY);
        }else{
            return true;
        }
    }
*/


}
