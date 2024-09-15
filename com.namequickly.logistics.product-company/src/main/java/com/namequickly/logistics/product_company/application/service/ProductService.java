package com.namequickly.logistics.product_company.application.service;

import com.namequickly.logistics.common.exception.GlobalException;
import com.namequickly.logistics.common.response.ResultCase;
import com.namequickly.logistics.product_company.application.dto.ProductCreateRequestDto;
import com.namequickly.logistics.product_company.application.dto.ProductCreateResponseDto;
import com.namequickly.logistics.product_company.application.dto.ProductDeleteResponseDto;
import com.namequickly.logistics.product_company.application.dto.ProductGetResponseDto;
import com.namequickly.logistics.product_company.application.dto.ProductUpdateRequestDto;
import com.namequickly.logistics.product_company.application.dto.ProductUpdateResponseDto;
import com.namequickly.logistics.product_company.application.mapper.ProductMapper;
import com.namequickly.logistics.product_company.domain.model.Product;
import com.namequickly.logistics.product_company.domain.model.Stock;
import com.namequickly.logistics.product_company.domain.repository.ProductRepository;
import com.namequickly.logistics.product_company.infrastructure.client.UserClient;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final VerifierService verifierService;
    private final UserClient userClient;

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

        String userName = (String) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
            .stream().map(GrantedAuthority::getAuthority) // 권한 문자열을 추출
            .findFirst() // 첫 번째 권한을 찾음
            .orElse(null);

        // TODO 나중에 feign client 개발 완료되면 주석 풀기
        /*
        verifierService.checkCompanyExists(requestDto.getSupplierId());
        verifierService.checkHubExists(requestDto.getHubId());

        if (userRole.equals("ROLE_HUBMANAGER")) {
            verifierService.isMatchHub(requestDto.getHubId(), userName);
        } else if (userRole.equals("ROLE_COMPANY")) {
            verifierService.isMatchCompany(requestDto.getSupplierId(), userName);
        }
        */

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
        String userName = (String) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
            .stream().map(GrantedAuthority::getAuthority) // 권한 문자열을 추출
            .findFirst() // 첫 번째 권한을 찾음
            .orElse(null);

        Product product = productRepository.findByProductIdAndIsDeleteFalse(productId)
            .orElseThrow(() -> new GlobalException(ResultCase.NOT_FOUND_PRODUCT));

        // TODO 나중에 feign client 개발 완료되면 주석 풀기
        /*
        if (userRole.equals("ROLE_HUBMANAGEMENT")) {
            verifierService.isMatchHub(product.getHubId(), userName);
        }
        */

        verifierService.checkProductInDelivery(productId);

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
        String userName = (String) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
            .stream().map(GrantedAuthority::getAuthority) // 권한 문자열을 추출
            .findFirst() // 첫 번째 권한을 찾음
            .orElse(null);

        // TODO 나중에 feign client 개발 완료되면 주석 풀기
        /*
        verifierService.checkCompanyExists(requestDto.getSupplierId());
        verifierService.checkHubExists(requestDto.getHubId());

        if (userRole.equals("ROLE_HUB")) {
            verifierService.isMatchHub(requestDto.getHubId(), userName);
        } else if (userRole.equals("ROLE_COMPANY")) {
            verifierService.isMatchCompany(requestDto.getSupplierId(), userName);
        }
         */

        Product product = productRepository.findByProductIdAndIsDeleteFalse(productId)
            .orElseThrow(() -> new GlobalException(ResultCase.NOT_FOUND_PRODUCT));

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

        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
            .stream().map(GrantedAuthority::getAuthority) // 권한 문자열을 추출
            .findFirst() // 첫 번째 권한을 찾음
            .orElse(null);

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        if (!userRole.equals("ROLE_MASTER") && isDelete) {
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
        String userName = (String) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
            .stream().map(GrantedAuthority::getAuthority) // 권한 문자열을 추출
            .findFirst() // 첫 번째 권한을 찾음
            .orElse(null);

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        // TODO 나중에 feign client 개발 완료되면 주석 풀기
        /*
        UserInfoDto userInfoDto = userClient.findUser(userName);

        if(userRole.equals("ROLE_HUB")){
            Page<Product> products = productRepository.findAllProductsByHubId(pageable, userInfoDto.getAffiliationId() , isDelete);
            return products.map(productMapper::toProductGetResponseDto);
        }else {
            Page<Product> products = productRepository.findAllProductsBySupplierId(pageable, userInfoDto.getAffiliationId() , isDelete);
            return products.map(productMapper::toProductGetResponseDto);
        }
        */
        return null;

    }
}
