package com.soat.fiap.food.core.api.catalog.application.mapper.request;

import com.soat.fiap.food.core.api.catalog.application.dto.request.ProductRequest;
import com.soat.fiap.food.core.api.catalog.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.domain.model.Stock;
import com.soat.fiap.food.core.api.catalog.domain.vo.Details;
import com.soat.fiap.food.core.api.catalog.domain.vo.ImageUrl;
import org.mapstruct.*;

import java.math.BigDecimal;

/**
 * Mapper que converte o DTO ProductRequest para a entidade de domínio Product
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductRequestMapper {

    /**
     * Converte um DTO de requisição para a entidade de domínio Product.
     *
     * @param request DTO de criação do produto
     * @return Entidade de domínio Product
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "auditInfo", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "details", source = "request", qualifiedByName = "mapToDetails")
    @Mapping(target = "imageUrl", source = "request.imageUrl", qualifiedByName = "mapToImageUrl")
    @Mapping(target = "stock", source = "request.stockQuantity", qualifiedByName = "mapToStock")
    Product toDomain(ProductRequest request);

    @Named("mapToDetails")
    default Details mapToDetails(ProductRequest request) {
        return new Details(request.getName(), request.getDescription());
    }

    @Named("mapToImageUrl")
    default ImageUrl mapToImageUrl(String imageUrl) {
        return (imageUrl != null && !imageUrl.isBlank()) ? new ImageUrl(imageUrl) : null;
    }

    @Named("mapToStock")
    default Stock mapToStock(Integer stockQuantity) {
        return new Stock(stockQuantity);
    }
}
