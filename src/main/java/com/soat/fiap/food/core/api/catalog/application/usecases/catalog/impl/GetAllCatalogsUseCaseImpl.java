package com.soat.fiap.food.core.api.catalog.application.usecases.catalog.impl;

import com.soat.fiap.food.core.api.catalog.application.dto.response.CatalogResponse;
import com.soat.fiap.food.core.api.catalog.application.mapper.response.CatalogResponseMapper;
import com.soat.fiap.food.core.api.catalog.application.usecases.catalog.GetAllCatalogsUseCase;
import com.soat.fiap.food.core.api.catalog.interfaces.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Caso de uso (implementação concreta): Obter todos catálogo.
 *
 */
@Slf4j
public class GetAllCatalogsUseCaseImpl implements GetAllCatalogsUseCase {

    private final CatalogResponseMapper catalogResponseMapper;
    private final CatalogGateway catalogGateway;

    public GetAllCatalogsUseCaseImpl(
            CatalogResponseMapper catalogResponseMapper,
            CatalogGateway catalogRepository
    ) {
        this.catalogResponseMapper = catalogResponseMapper;
        this.catalogGateway = catalogRepository;
    }

    /**
     * Lista todos os catálogos.
     *
     * @return Lista contendo todos os catálogos
     */
    @Override
    @Transactional(readOnly = true)
    public List<CatalogResponse> getAllCatalogs() {
        log.debug("Buscando todos os catálogos");
        var existingCatalogs = catalogGateway.findAll();
        log.debug("Encontradas {} catalogos", existingCatalogs.size());

        return catalogResponseMapper.toResponseList(existingCatalogs);
    }
}
