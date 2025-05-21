package com.soat.fiap.food.core.api.catalog.infrastructure.adapters.out.persistence.repository;

import com.soat.fiap.food.core.api.catalog.infrastructure.adapters.out.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório Spring Data JPA para CategoryEntity
 */
@Repository
public interface SpringDataCategoryRepository extends JpaRepository<CategoryEntity, Long> {
    
    /**
     * Busca todas categorias ativas
     * @return Lista de categorias ativas
     */
    List<CategoryEntity> findByActiveTrue();
    
    /**
     * Ordena as categorias pela ordem de exibição
     * @return Lista de categorias ordenadas
     */
    List<CategoryEntity> findAllByOrderByDisplayOrderAsc();
} 