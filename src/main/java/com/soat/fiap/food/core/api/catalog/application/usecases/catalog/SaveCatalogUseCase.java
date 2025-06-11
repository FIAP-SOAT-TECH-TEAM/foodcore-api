package com.soat.fiap.food.core.api.catalog.application.usecases.catalog;

import com.soat.fiap.food.core.api.catalog.application.dto.request.CatalogRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.response.CatalogResponse;

/**
 * Caso de uso: Salvar catálogo.
 *
 */
public interface SaveCatalogUseCase {

    /**
     * Salva um catálogo.
     *
     * @param catalogRequest Catálogo a ser salvo
     * @return Catálogo salvo com possíveis atualizações de identificadores
     */
    CatalogResponse saveCatalog(CatalogRequest catalogRequest);
}
