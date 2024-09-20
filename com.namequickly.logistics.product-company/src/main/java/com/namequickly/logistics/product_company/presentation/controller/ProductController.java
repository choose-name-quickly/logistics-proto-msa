package com.namequickly.logistics.product_company.presentation.controller;

import com.namequickly.logistics.common.response.CommonResponse;
import com.namequickly.logistics.product_company.application.dto.ProductCreateRequestDto;
import com.namequickly.logistics.product_company.application.dto.ProductCreateResponseDto;
import com.namequickly.logistics.product_company.application.dto.ProductDeleteResponseDto;
import com.namequickly.logistics.product_company.application.dto.ProductGetResponseDto;
import com.namequickly.logistics.product_company.application.dto.ProductUpdateRequestDto;
import com.namequickly.logistics.product_company.application.dto.ProductUpdateResponseDto;
import com.namequickly.logistics.product_company.application.dto.client.StockUpdateRequest;
import com.namequickly.logistics.product_company.application.service.ProductService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    // TODO 추후에 Role에 대한 Enum이 생기면 적용 예정 (ex UserRole.HUBMANAGEMENT)
    // 상품 추가
    @PreAuthorize("hasAnyAuthority('MASTER','HUBMANAGER','COMPANY')")
    @PostMapping("/products")
    public CommonResponse<ProductCreateResponseDto> createProduct(
        @Valid @RequestBody ProductCreateRequestDto requestDto) {
        ProductCreateResponseDto responseDto = productService.createProduct(requestDto);
        return CommonResponse.success(responseDto);
    }

    // 상품 삭제
    @PreAuthorize("hasAnyAuthority('MASTER','HUBMANAGER')")
    @DeleteMapping("/products/{product_id}")
    public CommonResponse<ProductDeleteResponseDto> deleteProduct(
        @PathVariable("product_id") UUID productId) {

        return CommonResponse.success(productService.deleteProduct(productId));
    }

    // 상품 수정
    @PreAuthorize("hasAnyAuthority('MASTER','HUBMANAGER','COMPANY')")
    @PutMapping("/products/{product_id}")
    public CommonResponse<ProductUpdateResponseDto> updateProduct(
        @PathVariable("product_id") UUID productId,
        @Valid @RequestBody ProductUpdateRequestDto requestDto) {
        return CommonResponse.success(productService.updateProduct(productId, requestDto));
    }

    // 상품 단건 조회
    @GetMapping("/products/{product_id}")
    public CommonResponse<ProductGetResponseDto> getProduct(
        @PathVariable("product_id") UUID productId) {
        ProductGetResponseDto productGetResponseDto = productService.getProduct(productId);
        return CommonResponse.success(productGetResponseDto);
    }

    // 상품 전체 조회
    @GetMapping("/products")
    public CommonResponse<Page<ProductGetResponseDto>> getAllProducts(
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "isAsc", defaultValue = "true") boolean isAsc,
        @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
        @RequestParam(name = "isDelete", defaultValue = "false") boolean isDelete) {
        Page<ProductGetResponseDto> productGetResponseDtos = productService.getAllProducts(page - 1,
            size, isAsc, sortBy, isDelete);
        return CommonResponse.success(productGetResponseDtos);
    }


    // 나와 관련된 상품 전체 조회
    // TODO 배송 기사는 상관 없겠지.. ?
    @PreAuthorize("hasAnyAuthority('HUBMANAGER','COMPANY')")
    @GetMapping("/products/mine")
    public CommonResponse<Page<ProductGetResponseDto>> getMyAllProducts(
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "isAsc", defaultValue = "true") boolean isAsc,
        @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
        @RequestParam(name = "isDelete", defaultValue = "false") boolean isDelete) {
        Page<ProductGetResponseDto> productGetResponseDtos = productService.getMyAllProducts(
            page - 1,
            size, isAsc, sortBy, isDelete);
        return CommonResponse.success(productGetResponseDtos);
    }

    // feign client를 위한 api
    @PutMapping("/product-companies/{product_id}/stocks")
    public Integer updateStockQuantity(@PathVariable("product_id") UUID productId,
        @RequestBody StockUpdateRequest stockUpdateRequest) {
        return productService.updateStockQuantity(productId, stockUpdateRequest);
    }
}