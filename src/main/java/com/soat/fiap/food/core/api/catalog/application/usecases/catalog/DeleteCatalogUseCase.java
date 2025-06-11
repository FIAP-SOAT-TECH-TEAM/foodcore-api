package com.soat.fiap.food.core.api.catalog.application.usecases.catalog;

import com.soat.fiap.food.core.api.catalog.application.dto.response.CatalogResponse;

import java.util.List;

/**
 * Caso de uso: Remover catálogo pelo seu identificador.
 *
 */
public interface DeleteCatalogUseCase {

    /**
     * Remove um catálogo pelo seu ID.
     *
     * @param id Identificador do catálogo a ser removido
     */
    void deleteCatalog(Long id);
}
