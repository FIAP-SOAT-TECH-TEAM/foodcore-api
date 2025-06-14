package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.controller.web.api.category;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.category.RemoveCategoryFromCatalogUseCase;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.DataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Excluir categoria.
 *
 */
@Slf4j
public class DeleteCategoryController {

    /**
     * Exclui uma categoria de um catálogo.
     *
     * @param catalogId  ID do catálogo
     * @param categoryId ID da categoria
     * @param dataSource Origem de dados para o gateway
     */
    public static void deleteCategory(Long catalogId, Long categoryId, DataSource dataSource) {
        log.debug("Excluindo categoria de id: {} do catalogo de id: {}", categoryId, catalogId);

        var gateway = new CatalogGateway(dataSource);

        var catalogWithoutCategory = RemoveCategoryFromCatalogUseCase.removeCategoryFromCatalog(catalogId, categoryId, gateway);

        gateway.save(catalogWithoutCategory);

        log.debug("Categoria excluída com sucesso: {}", categoryId);
    }
}
