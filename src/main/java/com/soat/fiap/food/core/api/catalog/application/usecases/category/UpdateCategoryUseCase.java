package com.soat.fiap.food.core.api.catalog.application.usecases.category;

import com.soat.fiap.food.core.api.catalog.application.dto.request.CategoryRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.response.CategoryResponse;

/**
 * Caso de uso: Atualizar categoria.
 *
 */
public interface UpdateCategoryUseCase {
    /**
     * Atualiza uma categoria.
     *
     * @param categoryRequest Categoria a ser atualizada
     * @return Categoria atualizada com possíveis atualizações de identificadores
     */
    CategoryResponse updateCategory(Long catalogId, Long categoryId, CategoryRequest categoryRequest);
}
