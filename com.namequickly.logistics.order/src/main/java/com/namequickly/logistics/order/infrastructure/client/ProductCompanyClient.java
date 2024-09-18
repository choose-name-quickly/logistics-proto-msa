package com.namequickly.logistics.order.infrastructure.client;

import com.namequickly.logistics.order.application.dto.client.StockUpdateRequest;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-company-service")
public interface ProductCompanyClient {

    @PutMapping("/api/product-companies/{product_id}/stocks")
    Integer updateStockQuantity(@PathVariable("product_id") UUID productId,
        @RequestBody StockUpdateRequest stockUpdateRequest);
}
