package com.soat.fiap.food.core.api.catalog.application.usecases.catalog.impl;

import com.soat.fiap.food.core.api.catalog.application.usecases.catalog.DeleteCatalogUseCase;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogConflictException;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.interfaces.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso (implementação concreta): Remover catálogo pelo seu identificador.
 *
 */
@Slf4j
public class DeleteCatalogUseCaseImpl implements DeleteCatalogUseCase {

    private final CatalogGateway catalogGateway;

    public DeleteCatalogUseCaseImpl(
            CatalogGateway catalogRepository
    ) {
        this.catalogGateway = catalogRepository;
    }

    /**
     * Remove um catálogo pelo seu ID.
     *
     * @param id Identificador do catálogo a ser removido
     */
    @Override
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
