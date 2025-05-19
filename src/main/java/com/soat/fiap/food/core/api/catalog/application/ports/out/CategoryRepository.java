package com.soat.fiap.food.core.api.catalog.application.ports.out;

import com.soat.fiap.food.core.api.catalog.domain.model.Category;
import java.util.List;
import java.util.Optional;

/**
 * Porta de saída para persistência de categorias
 */
public interface CategoryRepository {
    
    /**
     * Salva uma categoria
     * @param category Categoria a ser salva
     * @return Categoria salva com ID gerado
     */
    Category save(Category category);
    
    /**
     * Busca uma categoria por ID
     * @param id ID da categoria
     * @return Optional contendo a categoria ou vazio se não encontrada
     */
    Optional<Category> findById(Long id);
    
    /**
     * Lista todas as categorias
     * @return Lista de categorias
     */
    List<Category> findAll();
    
    /**
     * Remove uma categoria
     * @param id ID da categoria a ser removida
     */
    void delete(Long id);
} 