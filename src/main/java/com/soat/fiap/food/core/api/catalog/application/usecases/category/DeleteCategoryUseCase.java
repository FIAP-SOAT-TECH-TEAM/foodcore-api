package com.soat.fiap.food.core.api.catalog.application.usecases.category;

/**
 * Caso de uso: Remover categoria pelo seu identificador.
 *
 */
public interface DeleteCategoryUseCase {

    /**
     * Remove uma categoria de um catálogo.
     *
     * @param catalogId ID do catálogo
     * @param categoryId ID da categoria
     */
    void deleteCategory(Long catalogId, Long categoryId);
}
