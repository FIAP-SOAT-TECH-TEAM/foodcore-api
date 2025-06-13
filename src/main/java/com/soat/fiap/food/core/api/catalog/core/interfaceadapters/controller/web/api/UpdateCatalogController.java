package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.controller.web.api;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers.CatalogMapper;
import com.soat.fiap.food.core.api.catalog.core.application.usecases.catalog.UpdateCatalogUseCase;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.presenter.web.api.CatalogPresenter;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.DataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.requests.CatalogRequest;
import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.responses.CatalogResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Criar catálogo.
 *
 */
@Slf4j
public class UpdateCatalogController {

    /**
     * Atualiza um catálogo.
     *
     * @param catalogRequest Catálogo a ser atualizado
     * @param dataSource Origem de dados para o gateway
     * @return Catálogo atualizado com identificadores atualizados
     */
    public static CatalogResponse updateCatalog(Long id, CatalogRequest catalogRequest, DataSource dataSource) {

        var gateway = new CatalogGateway(dataSource);

        var catalogInput = CatalogMapper.toInput(catalogRequest);

        var catalog = UpdateCatalogUseCase.updateCatalog(id, catalogInput, gateway);

        var updatedCatalog = gateway.save(catalog);

        log.debug("Catalogo atualizado com sucesso: {}", id);

        return CatalogPresenter.toCatalogResponse(updatedCatalog);
    }
}
