package com.soat.fiap.food.core.api.catalog.application.usecases.product;

import com.soat.fiap.food.core.api.catalog.application.dto.request.ProductRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.response.ProductResponse;

/**
 * Caso de uso: Salvar produto.
 *
 */
public interface SaveProductUseCase {

    /**
     * Salva um produto.
     *
     * @param catalogId ID do catálogo
     * @param productRequest Produto a ser salvo
     * @return Produto salvo com identificadores atualizados
     */
    ProductResponse saveProduct(Long catalogId, ProductRequest productRequest);
}
