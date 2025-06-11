package com.soat.fiap.food.core.api.catalog.application.usecases.category;

import com.soat.fiap.food.core.api.catalog.application.dto.request.CategoryRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.response.CategoryResponse;

/**
 * Caso de uso: Salvar categoria.
 *
 */
public interface SaveCategoryUseCase {

    /**
     * Salva uma categoria.
     *
     * @param categoryRequest Categoria a ser salva
     * @return Categoria salva com possíveis atualizações de identificadores
     */
    CategoryResponse saveCategory(CategoryRequest categoryRequest);
}
