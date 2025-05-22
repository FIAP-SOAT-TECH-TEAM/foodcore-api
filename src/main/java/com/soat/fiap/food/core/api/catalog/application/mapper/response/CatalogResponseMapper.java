package com.soat.fiap.food.core.api.catalog.application.mapper.response;

import com.soat.fiap.food.core.api.catalog.application.dto.response.CatalogResponse;
import com.soat.fiap.food.core.api.catalog.domain.model.Catalog;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper que converte a entidade Catalog para o DTO CatalogResponse
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = CategoryResponseMapper.class)
public interface CatalogResponseMapper {

    CatalogResponse toResponse(Catalog catalog);

    List<CatalogResponse> toResponseList(List<Catalog> catalogs);
}
