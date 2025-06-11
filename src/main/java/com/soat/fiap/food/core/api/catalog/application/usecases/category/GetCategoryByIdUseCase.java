package com.soat.fiap.food.core.api.catalog.application.usecases.category;

import com.soat.fiap.food.core.api.catalog.application.dto.response.CategoryResponse;

/**
 * Caso de uso: Obter categoria por identificador.
 *
 */
public interface GetCategoryByIdUseCase {

    /**
     * Busca uma categoria dentro de um catálogo pelo seu ID.
     *
     * @param catalogId ID do catálogo
     * @param categoryId ID da categoria
     * @return Categoria encontrada
     */
    CategoryResponse getCategoryById(Long catalogId, Long categoryId);
}
