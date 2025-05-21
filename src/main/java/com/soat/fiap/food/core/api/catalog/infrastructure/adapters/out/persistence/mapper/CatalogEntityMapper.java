package com.soat.fiap.food.core.api.catalog.infrastructure.adapters.out.persistence.mapper;

import com.soat.fiap.food.core.api.catalog.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.infrastructure.adapters.out.persistence.entity.CatalogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper que converte entre a entidade de domínio Catalog e a entidade JPA CatalogEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CatalogEntityMapper {

    /**
     * Converte uma entidade JPA para uma entidade de domínio
     * @param entity Entidade JPA
     * @return Entidade de domínio
     */
    Catalog toDomain(CatalogEntity entity);

    /**
     * Converte uma lista de entidades JPA para uma lista de entidades de domínio
     * @param entities Lista de entidades JPA
     * @return Lista de entidades de domínio
     */
    List<Catalog> toDomainList(List<CatalogEntity> entities);

    /**
     * Converte uma entidade de domínio para uma entidade JPA
     * @param domain Entidade de domínio
     * @return Entidade JPA
     */
    CatalogEntity toEntity(Catalog domain);
}
