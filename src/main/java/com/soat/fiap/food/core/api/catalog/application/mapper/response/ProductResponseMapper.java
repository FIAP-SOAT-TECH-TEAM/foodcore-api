package com.soat.fiap.food.core.api.catalog.application.mapper.response;

import com.soat.fiap.food.core.api.catalog.application.dto.response.ProductResponse;
import com.soat.fiap.food.core.api.catalog.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.domain.vo.ImageUrl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper que converte a entidade Product para o DTO ProductResponse
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = StockResponseMapper.class)
public interface ProductResponseMapper {

    @Mapping(source = "imageUrl", target = "imageUrl", qualifiedByName = "mapCategoryImageUrlToString")
    ProductResponse toResponse(Product product);

    List<ProductResponse> toResponseList(List<Product> products);

    @Named("mapCategoryImageUrlToString")
    default String mapCategoryImageUrlToString(ImageUrl imageUrl) {
        return imageUrl != null ? imageUrl.value() : null;
    }
}
