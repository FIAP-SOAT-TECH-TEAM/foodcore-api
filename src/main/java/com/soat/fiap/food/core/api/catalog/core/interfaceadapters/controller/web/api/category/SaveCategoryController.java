package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.controller.web.api.category;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers.CategoryMapper;
import com.soat.fiap.food.core.api.catalog.core.application.usecases.category.AddCategoryToCatalogUseCase;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.presenter.web.api.CategoryPresenter;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.requests.CategoryRequest;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.CategoryResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Salvar categoria.
 *
 */
@Slf4j
public class SaveCategoryController {

    /**
     * Salva uma categoria.
     *
     * @param categoryRequest Categoria a ser salva
     * @param catalogDataSource Origem de dados para o gateway
     * @return Categoria salva com identificadores atualizados
     */
    public static CategoryResponse saveCategory(CategoryRequest categoryRequest, CatalogDataSource catalogDataSource) {

        var gateway = new CatalogGateway(catalogDataSource);

        var categoryInput = CategoryMapper.toInput(categoryRequest);

        var catalog = AddCategoryToCatalogUseCase.addCategoryToCatalog(categoryInput, gateway);

        var savedCatalog = gateway.save(catalog);

        var savedCategory = savedCatalog.getCategories().getLast();

        log.debug("Categoria criada com sucesso: {}", savedCategory.getId());

        return CategoryPresenter.toCategoryResponse(savedCategory);
    }
}
