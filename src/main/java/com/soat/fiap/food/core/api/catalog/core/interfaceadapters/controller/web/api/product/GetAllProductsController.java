package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.controller.web.api.product;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.product.GetAllProductsUseCase;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.presenter.web.api.ProductPresenter;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.DataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.ProductResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Controller: Obter todos os produtos.
 *
 */
@Slf4j
public class GetAllProductsController {

    /**
     * Obtém todos os produtos.

     * @param catalogId ID do catálogo
     * @param categoryId ID da categoria
     * @param dataSource Origem de dados para o gateway
     * @return Produtos encontrados
     */
    public static List<ProductResponse> getAllProducts(Long catalogId, Long categoryId, DataSource dataSource) {
        log.debug("Buscando todos os produtos da categoria de id: {}", categoryId);

        var gateway = new CatalogGateway(dataSource);

        var existingProducts = GetAllProductsUseCase.getAllProducts(catalogId, categoryId, gateway);

        return ProductPresenter.toListProductResponse(existingProducts);
    }
}
