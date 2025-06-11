package com.soat.fiap.food.core.api.catalog.application.usecases.category;

import org.springframework.web.multipart.MultipartFile;

/**
 * Caso de uso: Atualizar imagem da categoria.
 *
 */
public interface UpdateCategoryImageUseCase {

    /**
     * Atualiza apenas a imagem de uma categoria existente.
     *
     * @param catalogId ID do catálogo
     * @param categoryId ID da categoria do categoria
     * @param imageFile Arquivo da nova imagem
     */
    void updateCategoryImage(Long catalogId, Long categoryId, MultipartFile imageFile);
}
