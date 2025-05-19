package com.soat.fiap.food.core.api.catalog.application.ports.in;

import com.soat.fiap.food.core.api.catalog.domain.model.Category;
import java.util.List;
import java.util.Optional;

/**
 * Porta de entrada para operações relacionadas as categorias
 */
public interface CategoryUseCase {
    
    /**
     * Cadastra uma nova categoria
     * @param category Categoria a ser cadastrada
     * @return Categoria cadastrada com ID gerado
     */
    Category createCategory(Category category);
    
    /**
     * Atualiza uma categoria existente
     * @param id ID da categoria a ser atualizada
     * @param category Categoria com dados atualizados
     * @return Categoria atualizada
     */
    Category updateCategory(Long id, Category category);
    
    /**
     * Busca uma categoria por ID
     * @param id ID da categoria
     * @return Optional contendo a categoria ou vazio se não encontrada
     */
    Optional<Category> getCategoryById(Long id);
    
    /**
     * Lista todas as categorias
     * @return Lista de categorias
     */
    List<Category> getAllCategories();
    
    /**
     * Remove uma categoria
     * @param id ID da categoria a ser removida
     */
    void deleteCategory(Long id);
} 