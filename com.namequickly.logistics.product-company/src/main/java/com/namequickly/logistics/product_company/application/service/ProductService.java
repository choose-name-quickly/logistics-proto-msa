package com.namequickly.logistics.product_company.application.service;

import com.namequickly.logistics.common.exception.GlobalException;
import com.namequickly.logistics.common.response.ResultCase;
import com.namequickly.logistics.common.shared.UserRole;
import com.namequickly.logistics.product_company.application.dto.ProductCreateRequestDto;
import com.namequickly.logistics.product_company.application.dto.ProductCreateResponseDto;
import com.namequickly.logistics.product_company.application.dto.ProductDeleteResponseDto;
import com.namequickly.logistics.product_company.application.dto.ProductGetResponseDto;
import com.namequickly.logistics.product_company.application.dto.ProductUpdateRequestDto;
import com.namequickly.logistics.product_company.application.dto.ProductUpdateResponseDto;
import com.namequickly.logistics.product_company.application.dto.client.OperationType;
import com.namequickly.logistics.product_company.application.dto.client.StockUpdateRequest;
import com.namequickly.logistics.product_company.application.mapper.ProductMapper;
import com.namequickly.logistics.product_company.domain.model.Product;
import com.namequickly.logistics.product_company.domain.model.Stock;
import com.namequickly.logistics.product_company.domain.repository.ProductRepository;
import com.namequickly.logistics.product_company.infrastructure.security.CustomUserDetails;
import com.namequickly.logistics.product_company.infrastructure.security.SecurityUtils;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final FeignClientService feignClientService;

    private final ProductMapper productMapper;

    // TODO username, role 어떻게 전역 관리할 지 고민 필요
    // TODO 추후에 Role에 대한 Enum이 생기면 적용 예정 (ex UserRole.HUBMANAGEMENT)

    /**
     * 상품 등록
     *
     * @param requestDto
     * @return
     */
    @Transactional(readOnly = false)
    public ProductCreateResponseDto createProduct(@Valid ProductCreateRequestDto requestDto) {

        CustomUserDetails userDetails = SecurityUtils.getCurrentUserDetails();
        String userName = userDetails.getUsername();
        String affiliationId = userDetails.getAffiliationId();
        String userRole = userDetails.getRoleAsString();

        if (feignClientService.getCompanyById(requestDto.getSupplierId(), userRole) == null) {
            throw new GlobalException(ResultCase.NOT_FOUND_COMPANY);
        }

        if (feignClientService.getHub(requestDto.getHubId()) == null) {
            throw new GlobalException(ResultCase.NOT_FOUND_HUB);
        }

        if (userRole.equals(UserRole.HUBMANAGER.getAuthority())) {
            if (!affiliationId.equals(requestDto.getHubId().toString())) {
                throw new GlobalException(ResultCase.UNAUTHORIZED_HUB);
            }
        } else if (userRole.equals(UserRole.COMPANY.getAuthority())) {
            if (!affiliationId.equals(requestDto.getSupplierId().toString())) {
                throw new GlobalException(ResultCase.UNAUTHORIZED_COMPANY);
            }
        }

        if (productRepository.existsByProductName(requestDto.getProductName())) {
            throw new GlobalException(ResultCase.DUPLICATED_PRODUCT_NAME);
        }

        // product 생성
        Product product = Product.create(requestDto.getHubId(), requestDto.getProductName(),
            requestDto.getProductDescription(), requestDto.getSupplierId());

        // stock 생성
        Stock stock = Stock.create(requestDto.getStockQuantity(), product);
        product.addStockQuantity(stock);

        productRepository.save(product);

        return productMapper.toProductCreateResponseDto(product);
    }


    /**
     * 상품 삭제
     *
     * @param productId
     * @return
     */
    @Transactional(readOnly = false)
    public ProductDeleteResponseDto deleteProduct(UUID productId) {

        CustomUserDetails userDetails = SecurityUtils.getCurrentUserDetails();
        String userName = userDetails.getUsername();
        String affiliationId = userDetails.getAffiliationId();
        String userRole = userDetails.getRoleAsString();

        Product product = productRepository.findByProductIdAndIsDeleteFalse(productId)
            .orElseThrow(() -> new GlobalException(ResultCase.NOT_FOUND_PRODUCT));

        if (userRole.equals(UserRole.HUBMANAGER.getAuthority())) {
            if (!affiliationId.equals(product.getHubId().toString())) {
                throw new GlobalException(ResultCase.UNAUTHORIZED_HUB);
            }
        } else if (userRole.equals(UserRole.COMPANY.getAuthority())) {
            if (!affiliationId.equals(product.getSupplierId().toString())) {
                throw new GlobalException(ResultCase.UNAUTHORIZED_COMPANY);
            }
        }

        feignClientService.checkProductInDelivery(productId);

        product.deleteProduct(userName);

        return productMapper.toProductDeleteResponseDto(product);
    }

    /**
     * 상품 수정
     *
     * @param productId
     * @param requestDto
     * @return
     */
    @Transactional(readOnly = false)
    public ProductUpdateResponseDto updateProduct(UUID productId,
        ProductUpdateRequestDto requestDto) {

        CustomUserDetails userDetails = SecurityUtils.getCurrentUserDetails();
        String userName = userDetails.getUsername();
        String affiliationId = userDetails.getAffiliationId();
        String userRole = userDetails.getRoleAsString();

        Product product = productRepository.findByProductIdAndIsDeleteFalse(productId)
            .orElseThrow(() -> new GlobalException(ResultCase.NOT_FOUND_PRODUCT));

        if (userRole.equals(UserRole.HUBMANAGER.getAuthority())) {
            if (!affiliationId.equals(product.getHubId().toString())) {
                throw new GlobalException(ResultCase.UNAUTHORIZED_HUB);
            }
        } else if (userRole.equals(UserRole.COMPANY.getAuthority())) {
            if (!affiliationId.equals(product.getSupplierId().toString())) {
                throw new GlobalException(ResultCase.UNAUTHORIZED_COMPANY);
            }
        }

        productRepository.findByProductNameAndIsDeleteFalse(requestDto.getProductName())
            .filter(existingProduct -> !existingProduct.getProductId().equals(productId))
            .ifPresent(existingProduct -> {
                throw new GlobalException(ResultCase.DUPLICATED_PRODUCT_NAME);
            });

        product.updateProduct(requestDto.getProductName(), requestDto.getProductDescription(),
            requestDto.getStockQuantity());

        return productMapper.toProductUpdateResponseDto(product);
    }

    /**
     * 상품 단건 조회
     *
     * @param productId
     * @return
     */
    @Transactional(readOnly = true)
    public ProductGetResponseDto getProduct(UUID productId) {

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new GlobalException(ResultCase.NOT_FOUND_PRODUCT));

        return productMapper.toProductGetResponseDto(product);
    }


    /**
     * 상품 전체 조회
     *
     * @param page
     * @param size
     * @param isAsc
     * @param sortBy
     * @param isDelete
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ProductGetResponseDto> getAllProducts(int page, int size, boolean isAsc,
        String sortBy, boolean isDelete) {

        CustomUserDetails userDetails = SecurityUtils.getCurrentUserDetails();
        String userRole = userDetails.getRoleAsString();

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        if (!userRole.equals(UserRole.MASTER.getAuthority()) && isDelete) {
            throw new GlobalException(ResultCase.UNAUTHORIZED_DELETE_PRODUCT);
        }

        Page<Product> products = productRepository.findAllProducts(pageable, isDelete);
        return products.map(productMapper::toProductGetResponseDto);
    }


    /**
     * 나와 관련된 상품 전체 조회
     *
     * @param page
     * @param size
     * @param isAsc
     * @param sortBy
     * @param isDelete
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ProductGetResponseDto> getMyAllProducts(int page, int size, boolean isAsc,
        String sortBy, boolean isDelete) {

        CustomUserDetails userDetails = SecurityUtils.getCurrentUserDetails();
        String affiliationId = userDetails.getAffiliationId();
        String userRole = userDetails.getRoleAsString();

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        if (userRole.equals(UserRole.HUBMANAGER.getAuthority())) {
            Page<Product> products = productRepository.findAllProductsByHubId(pageable,
                UUID.fromString(affiliationId), isDelete);
            return products.map(productMapper::toProductGetResponseDto);
        } else {
            Page<Product> products = productRepository.findAllProductsBySupplierId(pageable,
                UUID.fromString(affiliationId),
                isDelete);
            return products.map(productMapper::toProductGetResponseDto);
        }

    }

    // feign client 메서드
    @Transactional(readOnly = false)
    public Integer updateStockQuantity(UUID productId, StockUpdateRequest stockUpdateRequest) {

        if (stockUpdateRequest.getStockQuantity() < 0) {
            throw new GlobalException(ResultCase.NEGATIVE_STOCK_QUANTITY);
        }

        Product product = productRepository.findByProductIdAndIsDeleteFalse(productId).orElseThrow(
            () -> new GlobalException(ResultCase.NOT_FOUND_PRODUCT)
        );

        int currentStockQuantity = product.getStock().getStockQuantity();
        int updatedStockQuantity;

        if (OperationType.INCREASE.equals(stockUpdateRequest.getOperationType())) {
            updatedStockQuantity = currentStockQuantity + stockUpdateRequest.getStockQuantity();

        } else if (OperationType.DECREASE.equals(stockUpdateRequest.getOperationType())) {
            updatedStockQuantity = currentStockQuantity - stockUpdateRequest.getStockQuantity();
            if (updatedStockQuantity < 0) {
                throw new GlobalException(ResultCase.OUT_OF_STOCK_QUNTITY);
            }
        } else {
            throw new GlobalException(ResultCase.INVALID_OPERATION);
        }

        product.getStock().updateStockQuantity(updatedStockQuantity);

        return updatedStockQuantity;
    }
}
