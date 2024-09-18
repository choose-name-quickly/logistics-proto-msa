package com.namequickly.logistics.product_company.infrastructure.client;

import com.namequickly.logistics.common.response.CommonResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-service")
public interface OrderClient {


    @GetMapping("/api/order-product/in-delivery")
    CommonResponse<Boolean> checkProductInDelivery(@RequestParam("productId") UUID productId);


}
