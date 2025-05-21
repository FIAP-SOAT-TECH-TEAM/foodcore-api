package com.soat.fiap.food.core.api.catalog.infrastructure.adapters.out.persistence.mapper;

import com.soat.fiap.food.core.api.catalog.domain.model.Category;
import com.soat.fiap.food.core.api.catalog.infrastructure.adapters.out.persistence.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper que converte entre a entidade de domínio Category e a entidade JPA CategoryEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryEntityMapper {
    
    /**
     * Converte uma entidade JPA para uma entidade de domínio
     * @param entity Entidade JPA
     * @return Entidade de domínio
     */
    Category toDomain(CategoryEntity entity);
    
    /**
     * Converte uma lista de entidades JPA para uma lista de entidades de domínio
     * @param entities Lista de entidades JPA
     * @return Lista de entidades de domínio
     */
    List<Category> toDomainList(List<CategoryEntity> entities);
    
    /**
     * Converte uma entidade de domínio para uma entidade JPA
     * @param domain Entidade de domínio
     * @return Entidade JPA
     */
    CategoryEntity toEntity(Category domain);
} 