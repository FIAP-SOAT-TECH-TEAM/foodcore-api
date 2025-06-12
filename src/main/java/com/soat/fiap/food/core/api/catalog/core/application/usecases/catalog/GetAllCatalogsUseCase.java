package com.soat.fiap.food.core.api.catalog.core.application.usecases.catalog;

import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.responses.CatalogResponse;
import com.soat.fiap.food.core.api.catalog.core.application.mapper.response.CatalogResponseMapper;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Caso de uso: Obter todos catálogo.
 *
 */
@Slf4j
public class GetAllCatalogsUseCase {

    private final CatalogResponseMapper catalogResponseMapper;
    private final CatalogGateway catalogGateway;

    public GetAllCatalogsUseCase(
            CatalogResponseMapper catalogResponseMapper,
            CatalogGateway catalogGateway
    ) {
        this.catalogResponseMapper = catalogResponseMapper;
        this.catalogGateway = catalogGateway;
    }

    /**
     * Lista todos os catálogos.
     *
     * @return Lista contendo todos os catálogos
     */
    public List<CatalogResponse> getAllCatalogs() {
        log.debug("Buscando todos os catálogos");
        var existingCatalogs = catalogGateway.findAll();
        log.debug("Encontradas {} catalogos", existingCatalogs.size());

        return catalogResponseMapper.toResponseList(existingCatalogs);
    }
}
