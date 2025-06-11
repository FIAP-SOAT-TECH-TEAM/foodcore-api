package com.soat.fiap.food.core.api.catalog.application.usecases.catalog;

import com.soat.fiap.food.core.api.catalog.application.dto.request.CatalogRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.response.CatalogResponse;

/**
 * Caso de uso: Atualizar catálogo.
 *
 */
public interface UpdateCatalogUseCase {
    /**
     * Atualiza um catálogo.
     *
     * @param id ID do Catálogo a ser atualizado
     * @param catalogRequest Catálogo a ser atualizado
     * @return Catálogo atualizado com possíveis atualizações de identificadores
     */
    CatalogResponse updateCatalog(Long id, CatalogRequest catalogRequest);
}
