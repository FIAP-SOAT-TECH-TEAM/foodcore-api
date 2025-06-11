package com.soat.fiap.food.core.api.catalog.application.usecases.product;

import org.springframework.web.multipart.MultipartFile;

/**
 * Caso de uso: Atualizar imagem do produto.
 *
 */
public interface UpdateProductImageUseCase {

    /**
     * Atualiza apenas a imagem de um produto existente.
     *
     * @param catalogId ID do catálogo
     * @param categoryId ID da categoria do produto
     * @param productId ID do produto
     * @param imageFile Arquivo da nova imagem
     */
    void updateProductImage(Long catalogId, Long categoryId, Long productId, MultipartFile imageFile);
}
