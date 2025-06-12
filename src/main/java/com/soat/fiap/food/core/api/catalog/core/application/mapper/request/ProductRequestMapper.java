package com.soat.fiap.food.core.api.catalog.core.application.mapper.request;

import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.requests.ProductRequest;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Stock;
import com.soat.fiap.food.core.api.catalog.core.domain.vo.Details;
import org.mapstruct.*;

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
    @Mapping(target = "stock", source = "request.stockQuantity", qualifiedByName = "mapToStock")
    Product toDomain(ProductRequest request);

    @Named("mapToDetails")
    default Details mapToDetails(ProductRequest request) {
        return new Details(request.getName(), request.getDescription());
    }

    @Named("mapToStock")
    default Stock mapToStock(Integer stockQuantity) {
        return new Stock(stockQuantity);
    }
}
