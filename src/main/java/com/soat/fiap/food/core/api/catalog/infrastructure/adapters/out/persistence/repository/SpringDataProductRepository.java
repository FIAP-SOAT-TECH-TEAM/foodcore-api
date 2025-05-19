package com.soat.fiap.food.core.api.catalog.infrastructure.adapters.out.persistence.repository;

import com.soat.fiap.food.core.api.catalog.infrastructure.adapters.out.persistence.entity.CategoryEntity;
import com.soat.fiap.food.core.api.catalog.infrastructure.adapters.out.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório Spring Data JPA para ProductEntity
 */
@Repository
public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Long> {
    
    /**
     * Busca produtos por categoria
     * @param category Categoria dos produtos
     * @return Lista de produtos da categoria
     */
    List<ProductEntity> findByCategory(CategoryEntity category);
    
    /**
     * Busca produtos por categoria ordenados
     * @param category Categoria dos produtos
     * @return Lista de produtos da categoria ordenados
     */
    List<ProductEntity> findByCategoryOrderByDisplayOrderAsc(CategoryEntity category);
    
    /**
     * Busca todos os produtos ativos
     * @return Lista de produtos ativos
     */
    List<ProductEntity> findByActiveTrue();
    
    /**
     * Busca produtos ativos por categoria
     * @param category Categoria dos produtos
     * @return Lista de produtos ativos da categoria
     */
    List<ProductEntity> findByCategoryAndActiveTrue(CategoryEntity category);
    
    /**
     * Busca produtos ordenados
     * @return Lista de produtos ordenados
     */
    List<ProductEntity> findAllByOrderByDisplayOrderAsc();
} 