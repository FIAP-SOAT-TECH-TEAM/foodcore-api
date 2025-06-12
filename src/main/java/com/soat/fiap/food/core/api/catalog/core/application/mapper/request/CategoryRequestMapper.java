package com.soat.fiap.food.core.api.catalog.core.application.mapper.request;

import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.requests.CategoryRequest;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Category;
import com.soat.fiap.food.core.api.catalog.core.domain.vo.Details;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper que converte o DTO CategoryRequest para a entidade de domínio Category
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryRequestMapper {

    /**
     * Converte um DTO de requisição para a entidade de domínio Category.
     *
     * @param request DTO de criação da categoria
     * @return Entidade de domínio Category
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "catalog", ignore = true)
    @Mapping(target = "auditInfo", ignore = true)
    @Mapping(target = "details", source = "request", qualifiedByName = "mapToDetails")
    Category toDomain(CategoryRequest request);

    @Named("mapToDetails")
    default Details mapToDetails(CategoryRequest request) {
        return new Details(request.getName(), request.getDescription());
    }
}