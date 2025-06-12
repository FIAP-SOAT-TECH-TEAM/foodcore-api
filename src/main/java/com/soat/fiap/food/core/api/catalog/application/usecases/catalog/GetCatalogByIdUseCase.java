package com.soat.fiap.food.core.api.catalog.application.usecases.catalog;

import com.soat.fiap.food.core.api.catalog.application.dto.response.CatalogResponse;
import com.soat.fiap.food.core.api.catalog.application.mapper.response.CatalogResponseMapper;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.interfaces.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Obter catálogo por identificador.
 *
 */
@Slf4j
public class GetCatalogByIdUseCase {

    private final CatalogResponseMapper catalogResponseMapper;
    private final CatalogGateway catalogGateway;

    public GetCatalogByIdUseCase(
            CatalogResponseMapper catalogResponseMapper,
            CatalogGateway catalogGateway
    ) {
        this.catalogResponseMapper = catalogResponseMapper;
        this.catalogGateway = catalogGateway;
    }

    /**
     * Busca um catálogo pelo seu ID.

     * @param id Identificador do catálogo
     * @return o catálogo
     */
    public CatalogResponse getCatalogById(Long id) {
        log.debug("Buscando catalogo de id: {}", id);

        var existingCatalog = catalogGateway.findById(id);

        if (existingCatalog.isEmpty()) {
            log.warn("Catalogo não encontrado. Id: {}", id);
            throw new CatalogNotFoundException("Catalogo", id);
        }

        return catalogResponseMapper.toResponse(existingCatalog.get());
    }
}
