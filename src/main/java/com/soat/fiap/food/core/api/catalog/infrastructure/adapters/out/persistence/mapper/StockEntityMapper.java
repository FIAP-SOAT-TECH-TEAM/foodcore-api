package com.soat.fiap.food.core.api.catalog.infrastructure.adapters.out.persistence.mapper;

import com.soat.fiap.food.core.api.catalog.domain.model.Stock;
import com.soat.fiap.food.core.api.catalog.infrastructure.adapters.out.persistence.entity.StockEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper que converte entre a entidade de domínio Stock e a entidade JPA StockEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StockEntityMapper {

    /**
     * Converte uma entidade JPA para uma entidade de domínio
     * @param entity Entidade JPA
     * @return Entidade de domínio
     */
    Stock toDomain(StockEntity entity);

    /**
     * Converte uma lista de entidades JPA para uma lista de entidades de domínio
     * @param entities Lista de entidades JPA
     * @return Lista de entidades de domínio
     */
    List<Stock> toDomainList(List<StockEntity> entities);

    /**
     * Converte uma entidade de domínio para uma entidade JPA
     * @param domain Entidade de domínio
     * @return Entidade JPA
     */
    StockEntity toEntity(Stock domain);
}