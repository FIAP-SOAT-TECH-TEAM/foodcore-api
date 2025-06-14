package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.controller.web.api.catalog;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.catalog.DeleteCatalogUseCase;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.DataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Excluir catálogo.
 *
 */
@Slf4j
public class DeleteCatalogController {

    /**
     * Remove um catálogo pelo seu ID.
     *
     * @param id Identificador do catálogo a ser removido
     * @param dataSource Origem de dados para o gateway
     */
    public static void deleteCatalog(Long id, DataSource dataSource) {
        log.debug("Excluindo catalogo de id: {}", id);

        var gateway = new CatalogGateway(dataSource);

        DeleteCatalogUseCase.deleteCatalog(id, gateway);
    }
}
