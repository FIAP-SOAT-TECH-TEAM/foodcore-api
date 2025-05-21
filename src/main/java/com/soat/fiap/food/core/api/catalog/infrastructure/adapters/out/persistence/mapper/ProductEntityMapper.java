package com.soat.fiap.food.core.api.catalog.infrastructure.adapters.out.persistence.mapper;

import com.soat.fiap.food.core.api.catalog.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.infrastructure.adapters.out.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper que converte entre a entidade de domínio Product e a entidade JPA ProductEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductEntityMapper {

    /**
     * Converte uma entidade JPA para uma entidade de domínio
     * @param entity Entidade JPA
     * @return Entidade de domínio
     */
    Product toDomain(ProductEntity entity);

    /**
     * Converte uma lista de entidades JPA para uma lista de entidades de domínio
     * @param entities Lista de entidades JPA
     * @return Lista de entidades de domínio
     */
    List<Product> toDomainList(List<ProductEntity> entities);

    /**
     * Converte uma entidade de domínio para uma entidade JPA
     * @param domain Entidade de domínio
     * @return Entidade JPA
     */
    ProductEntity toEntity(Product domain);
}