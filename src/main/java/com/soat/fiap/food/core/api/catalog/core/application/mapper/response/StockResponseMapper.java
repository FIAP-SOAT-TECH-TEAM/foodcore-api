package com.soat.fiap.food.core.api.catalog.core.application.mapper.response;

import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.responses.StockResponse;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Stock;
import com.soat.fiap.food.core.api.shared.mapper.AuditInfoMapper;
import com.soat.fiap.food.core.api.shared.mapper.CycleAvoidingMappingContext;
import com.soat.fiap.food.core.api.shared.mapper.DoIgnore;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper que converte a entidade {@link Stock} para o DTO {@link StockResponse}.
 * Utiliza {@link ProductResponseMapper} para conversão de produtos associados ao estoque.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ProductResponseMapper.class, AuditInfoMapper.class})
public interface StockResponseMapper {

    /**
     * Converte a entidade {@link Stock} para {@link StockResponse}, utilizando um contexto para evitar ciclos.
     *
     * @param stock Entidade Stock.
     * @param cycleAvoidingMappingContext Contexto para evitar ciclos de mapeamento.
     * @return DTO StockResponse.
     */
    @Mapping(source = "auditInfo", target = "createdAt", qualifiedByName = "mapCreatedAt")
    @Mapping(source = "auditInfo", target = "updatedAt", qualifiedByName = "mapUpdatedAt")
    StockResponse toResponse(Stock stock, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    /**
     * Converte a entidade {@link Stock} para {@link StockResponse} com um contexto padrão.
     *
     * @param stock Entidade Stock.
     * @return DTO StockResponse.
     */
    @DoIgnore
    default StockResponse toResponse(Stock stock) {
        return toResponse(stock, new CycleAvoidingMappingContext());
    }
}