package com.soat.fiap.food.core.api.catalog.application.usecases.product;

import com.soat.fiap.food.core.api.catalog.application.dto.request.CategoryRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.request.ProductRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.response.CategoryResponse;
import com.soat.fiap.food.core.api.catalog.application.dto.response.ProductResponse;

/**
 * Caso de uso: Atualizar produto.
 *
 */
public interface UpdateProductUseCase {

    /**
     * Atualiza um produto.
     *
     * @param catalogId ID do catálogo
     * @param categoryId ID da categoria
     * @param productId ID do produto a ser atualizado
     * @param productRequest Produto com os dados atualizados
     * @return Produto atualizado com identificadores atualizados
     */
    ProductResponse updateProduct(Long catalogId, Long categoryId, Long productId, ProductRequest productRequest);
}
