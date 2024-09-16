package com.namequickly.logistics.order.application.service;

import com.namequickly.logistics.order.infrastructure.client.CompanyClient;
import com.namequickly.logistics.order.infrastructure.client.HubClient;
import com.namequickly.logistics.order.infrastructure.client.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VerifierService {

    private final CompanyClient companyClient;
    private final HubClient hubClient;
    private final UserClient userClient;

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
