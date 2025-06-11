package com.soat.fiap.food.core.api.catalog.application.usecases.product;

import com.soat.fiap.food.core.api.catalog.application.dto.response.ProductResponse;

import java.util.List;

/**
 * Caso de uso: Obter todos produtos.
 *
 */
public interface GetAllProductsUseCase {

    /**
     * Lista todas os produtos de ua categoria.
     *
     * @param catalogId ID do catálogo
     * @param categoryId ID da categoria
     * @return Lista de produtos de uma categoria
     */
    List<ProductResponse> getAllProducts(Long catalogId, Long categoryId);
}
