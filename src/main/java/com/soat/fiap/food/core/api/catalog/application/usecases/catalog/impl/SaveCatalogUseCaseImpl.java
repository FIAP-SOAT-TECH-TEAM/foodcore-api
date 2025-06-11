package com.soat.fiap.food.core.api.catalog.application.usecases.catalog.impl;

import com.soat.fiap.food.core.api.catalog.application.dto.request.CatalogRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.response.CatalogResponse;
import com.soat.fiap.food.core.api.catalog.application.mapper.request.CatalogRequestMapper;
import com.soat.fiap.food.core.api.catalog.application.mapper.response.CatalogResponseMapper;
import com.soat.fiap.food.core.api.catalog.application.usecases.catalog.SaveCatalogUseCase;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogConflictException;
import com.soat.fiap.food.core.api.catalog.interfaces.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso (implementação concreta): Salvar catálogo.
 *
 */
@Slf4j
public class SaveCatalogUseCaseImpl implements SaveCatalogUseCase {

    private final CatalogRequestMapper catalogRequestMapper;
    private final CatalogResponseMapper catalogResponseMapper;
    private final CatalogGateway catalogGateway;

    public SaveCatalogUseCaseImpl (
            CatalogRequestMapper catalogRequestMapper,
            CatalogResponseMapper catalogResponseMapper,
            CatalogGateway catalogRepository
    ) {
        this.catalogRequestMapper = catalogRequestMapper;
        this.catalogResponseMapper = catalogResponseMapper;
        this.catalogGateway = catalogRepository;
    }

    /**
     * Salva um catálogo.
     *
     * @param catalogRequest Catálogo a ser salvo
     * @return Catálogo salvo com identificadores atualizados
     */
    @Override
    public CatalogResponse saveCatalog(CatalogRequest catalogRequest) {

        var catalog = catalogRequestMapper.toDomain(catalogRequest);
        log.debug("Criando catalogo: {}", catalog.getName());

        if (catalogGateway.existsByName(catalog.getName())) {
            log.warn("Tentativa de cadastrar catalogo com nome repetido. Nome: {}", catalog.getName());
            throw new CatalogConflictException("Catalogo", "Nome", catalog.getName());
        }

        var savedCatalog = catalogGateway.save(catalog);

        log.debug("Catalogo criado com sucesso: {}", catalog.getId());

        return catalogResponseMapper.toResponse(savedCatalog);
    }
}
