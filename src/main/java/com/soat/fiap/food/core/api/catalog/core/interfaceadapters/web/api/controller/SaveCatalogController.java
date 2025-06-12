package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.web.api.controller;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers.CatalogMapper;
import com.soat.fiap.food.core.api.catalog.core.application.usecases.catalog.CreateCatalogUseCase;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.web.api.presenter.CatalogPresenter;
import com.soat.fiap.food.core.api.catalog.infrastructure.shared.DataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.requests.CatalogRequest;
import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.responses.CatalogResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Criar catálogo.
 *
 */
@Slf4j
public class SaveCatalogController {

    /**
     * Salva um catálogo.
     *
     * @param catalogRequest Catálogo a ser salvo
     * @return Catálogo salvo com identificadores atualizados
     */
    public static CatalogResponse saveCatalog(CatalogRequest catalogRequest, DataSource dataSource) {

        var gateway = new CatalogGateway(dataSource);
        var catalogInput = CatalogMapper.toInput(catalogRequest);

        log.debug("Criando catalogo: {}", catalogInput.getName());

        var catalog = CreateCatalogUseCase.createCatalogUseCase(catalogInput, gateway);

        var savedCatalog = gateway.save(catalog);

        log.debug("Catalogo criado com sucesso: {}", savedCatalog.getId());

        return CatalogPresenter.toCatalogResponse(catalog);
    }
}
