package com.soat.fiap.food.core.api.catalog.infrastructure.adapters.out.persistence.mapper;

import com.soat.fiap.food.core.api.catalog.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.infrastructure.adapters.out.persistence.entity.ProductEntity;
import com.soat.fiap.food.core.api.shared.mapper.CycleAvoidingMappingContext;
import com.soat.fiap.food.core.api.shared.mapper.DoIgnore;
import org.mapstruct.Context;
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
     * @param cycleAvoidingMappingContext Contexto para evitar ciclos
     * @return Entidade de domínio
     */
    Product toDomain(ProductEntity entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    /**
     * Converte uma lista de entidades JPA para uma lista de entidades de domínio
     * @param entities Lista de entidades JPA
     * @param cycleAvoidingMappingContext Contexto para evitar ciclos
     * @return Lista de entidades de domínio
     */
    List<Product> toDomainList(List<ProductEntity> entities, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

    /**
     * Converte uma entidade de domínio para uma entidade JPA
     * @param domain Entidade de domínio
     * @return Entidade JPA
     */
    ProductEntity toEntity(Product domain);

    @DoIgnore
    default Product toDomain(ProductEntity entity) {
        return toDomain(entity, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default List<Product> toDomainList(List<ProductEntity> entities) {
        return toDomainList(entities, new CycleAvoidingMappingContext());
    }
}