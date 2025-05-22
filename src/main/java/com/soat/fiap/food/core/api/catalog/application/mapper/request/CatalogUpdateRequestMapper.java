package com.soat.fiap.food.core.api.catalog.application.mapper.request;

import com.soat.fiap.food.core.api.catalog.application.dto.request.CatalogUpdateRequest;
import com.soat.fiap.food.core.api.catalog.domain.model.Catalog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper que converte o DTO CatalogUpdateRequest para a entidade de domínio Catalog
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CatalogUpdateRequestMapper {

    /**
     * Converte um DTO de atualização para a entidade de domínio Catalog.
     *
     * @param request DTO de atualização do catálogo
     * @return Entidade de domínio Catalog
     */
    @Mapping(target = "categories", ignore = true)
    Catalog toDomain(CatalogUpdateRequest request);
}