package com.soat.fiap.food.core.api.catalog.application.usecases.catalog;

import com.soat.fiap.food.core.api.catalog.application.dto.request.CatalogRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.response.CatalogResponse;
import com.soat.fiap.food.core.api.catalog.application.mapper.request.CatalogRequestMapper;
import com.soat.fiap.food.core.api.catalog.application.mapper.response.CatalogResponseMapper;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogConflictException;
import com.soat.fiap.food.core.api.catalog.interfaces.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Salvar catálogo.
 *
 */
@Slf4j
public class SaveCatalogUseCase {

    private final CatalogRequestMapper catalogRequestMapper;
    private final CatalogResponseMapper catalogResponseMapper;
    private final CatalogGateway catalogGateway;

    public SaveCatalogUseCase(
            CatalogRequestMapper catalogRequestMapper,
            CatalogResponseMapper catalogResponseMapper,
            CatalogGateway catalogGateway
    ) {
        this.catalogRequestMapper = catalogRequestMapper;
        this.catalogResponseMapper = catalogResponseMapper;
        this.catalogGateway = catalogGateway;
    }

    /**
     * Salva um catálogo.
     *
     * @param catalogRequest Catálogo a ser salvo
     * @return Catálogo salvo com identificadores atualizados
     */
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
