package com.soat.fiap.food.core.api.catalog.application.usecases.category;

import com.soat.fiap.food.core.api.catalog.application.dto.response.CategoryResponse;

import java.util.List;

/**
 * Caso de uso: Obter todas categorias.
 *
 */
public interface GetAllCategoriesUseCase {

    /**
     * Lista todas as categorias de um catálogo.
     *
     * @param catalogId ID do catálogo
     * @return Lista de categorias do catálogo
     */
    List<CategoryResponse> getAllCategories(Long catalogId);
}
