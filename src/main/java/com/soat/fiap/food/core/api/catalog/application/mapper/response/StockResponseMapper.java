package com.soat.fiap.food.core.api.catalog.application.mapper.response;

import com.soat.fiap.food.core.api.catalog.application.dto.response.StockResponse;
import com.soat.fiap.food.core.api.catalog.domain.model.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper que converte a entidade Stock para o DTO StockResponse
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = ProductResponseMapper.class)
public interface StockResponseMapper {

    StockResponse toResponse(Stock stock);
}
