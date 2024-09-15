package com.namequickly.logistics.product_company.application.mapper;


import com.namequickly.logistics.product_company.application.dto.ProductCreateResponseDto;
import com.namequickly.logistics.product_company.application.dto.ProductDeleteResponseDto;
import com.namequickly.logistics.product_company.application.dto.ProductGetResponseDto;
import com.namequickly.logistics.product_company.application.dto.ProductUpdateRequestDto;
import com.namequickly.logistics.product_company.application.dto.ProductUpdateResponseDto;
import com.namequickly.logistics.product_company.domain.model.Product;
import com.namequickly.logistics.product_company.domain.model.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    //ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "productId", ignore = true)  // 업데이트 시 productId는 무시
    Product toProduct(ProductUpdateRequestDto requestDto);

    @Mapping(source = "stockQuantity", target = "stockQuantity")
    Stock toStock(ProductUpdateRequestDto requestDto);

    @Mapping(source = "product.stock.stockQuantity", target = "stockQuantity")
    @Mapping(source = "product.createdAt", target = "createdAt")
    @Mapping(source = "product.updatedAt", target = "updatedAt")
    @Mapping(source = "product.isDelete", target = "isDelete")
    ProductGetResponseDto toProductGetResponseDto(Product product);
    @Mapping(source = "product.createdAt", target = "createdAt")
    ProductCreateResponseDto toProductCreateResponseDto(Product product);
    @Mapping(source = "product.updatedAt", target = "updatedAt")
    @Mapping(source = "product.stock.stockQuantity", target = "stockQuantity")
    ProductUpdateResponseDto toProductUpdateResponseDto(Product product);
    @Mapping(source = "product.deletedAt", target = "deletedAt")
    ProductDeleteResponseDto toProductDeleteResponseDto(Product product);
}
