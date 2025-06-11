package com.soat.fiap.food.core.api.catalog.application.usecases.product;

import com.soat.fiap.food.core.api.catalog.application.dto.response.ProductResponse;

/**
 * Caso de uso: Obter produto por identificador.
 *
 */
public interface GetProductByIdUseCase {

    /**
     * Busca um produto por ID dentro de uma categoria.
     *
     * @param catalogId ID do catálogo
     * @param categoryId ID da categoria
     * @param productId ID do produto
     * @return Produto encontrado
     */
    ProductResponse getProductById(Long catalogId, Long categoryId, Long productId);
}
