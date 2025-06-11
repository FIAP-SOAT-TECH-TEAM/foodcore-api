package com.soat.fiap.food.core.api.catalog.application.usecases.product;

/**
 * Caso de uso: Remover produto pelo seu identificador.
 *
 */
public interface DeleteProductUseCase {

    /**
     * Remove um produto de uma categoria.
     *
     * @param catalogId ID do catálogo
     * @param categoryId ID da categoria
     * @param productId ID do produto a ser removido
     */
    void deleteProduct(Long catalogId, Long categoryId, Long productId);
}
