package com.soat.fiap.food.core.api.catalog.core.application.usecases.catalog;

import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogConflictException;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Remover catálogo pelo seu identificador.
 *
 */
@Slf4j
public class DeleteCatalogUseCase {

    private final CatalogGateway catalogGateway;

    public DeleteCatalogUseCase(
            CatalogGateway catalogGateway
    ) {
        this.catalogGateway = catalogGateway;
    }

    /**
     * Remove um catálogo pelo seu ID.
     *
     * @param id Identificador do catálogo a ser removido
     */
    public void deleteCatalog(Long id) {
        log.debug("Excluindo catalogo de id: {}", id);

        if (!catalogGateway.existsById(id)) {
            log.warn("Tentativa de excluir catalogo inexistente. Id: {}", id);
            throw new CatalogNotFoundException("Catalogo", id);
        }
        if (catalogGateway.existsCategoryByCatalogId(id)) {
            log.warn("Tentativa de excluir catalogo com categorias associadas. Id: {}", id);
            throw new CatalogConflictException("Não é possível excluir este catalogo porque existem categorias associadas a ele");
        }

        catalogGateway.delete(id);

        log.debug("Catalogo excluido com sucesso: {}", id);
    }
}
