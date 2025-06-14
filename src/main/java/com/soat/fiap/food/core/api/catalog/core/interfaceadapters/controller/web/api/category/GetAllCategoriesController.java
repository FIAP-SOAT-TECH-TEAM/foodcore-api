package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.controller.web.api.category;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.category.GetAllCategoriesUseCase;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.presenter.web.api.CategoryPresenter;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.DataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.CategoryResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Controller: Obter todos as categorias.
 *
 */
@Slf4j
public class GetAllCategoriesController {

    /**
     * Obtém todos as categorias.

     * @param catalogId ID do catálogo
     * @param dataSource Origem de dados para o gateway
     * @return Categorias encontradas
     */
    public static List<CategoryResponse> getAllCategories(Long catalogId, DataSource dataSource) {
        log.debug("Buscando todas as categorias do catalogo de id: {}", catalogId);

        var gateway = new CatalogGateway(dataSource);

        var existingCategories = GetAllCategoriesUseCase.getAllCategories(catalogId, gateway);

        return CategoryPresenter.toListCategoryResponse(existingCategories);
    }
}
