package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.controller.web.api.catalog;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.catalog.GetAllCatalogsUseCase;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.presenter.web.api.CatalogPresenter;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.CatalogResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Controller: Obter todos os catálogos.
 *
 */
@Slf4j
public class GetAllCatalogsController {

    /**
     * Obtém todos os catálogos.

     * @param catalogDataSource Origem de dados para o gateway
     * @return o catálogo
     */
    public static List<CatalogResponse> getAllCatalogs(CatalogDataSource catalogDataSource) {
        log.debug("Buscando todos os catálogos");

        var gateway = new CatalogGateway(catalogDataSource);

        var existingCatalog = GetAllCatalogsUseCase.getAllCatalogs(gateway);

        return CatalogPresenter.toListCatalogResponse(existingCatalog);
    }
}
