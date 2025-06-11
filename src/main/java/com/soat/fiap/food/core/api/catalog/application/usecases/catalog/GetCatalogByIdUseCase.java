package com.soat.fiap.food.core.api.catalog.application.usecases.catalog;

import com.soat.fiap.food.core.api.catalog.application.dto.request.CatalogRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.response.CatalogResponse;

/**
 * Caso de uso: Obter catálogo por identificador.
 *
 */
public interface GetCatalogByIdUseCase {

    /**
     * Busca um catálogo pelo seu ID.
     *
     * @param id Identificador do catálogo
     * @return catálogo caso encontrado
     */
    CatalogResponse getCatalogById(Long id);
}
