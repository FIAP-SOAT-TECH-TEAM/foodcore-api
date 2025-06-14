package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.controller.web.api.product;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers.ProductMapper;
import com.soat.fiap.food.core.api.catalog.core.application.usecases.product.AddProductToCategoryUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.events.ProductCreatedEvent;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.EventPublisherGateway;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.presenter.web.api.ProductPresenter;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.DataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.requests.ProductRequest;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.ProductResponse;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.EventPublisherSource;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Salvar produto.
 */
@Slf4j
public class SaveProductController {

    /**
     * Salva um produto em uma categoria específica de um catálogo.
     *
     * @param catalogId ID do catálogo
     * @param productRequest  Produto a ser salvo
     * @param dataSource      Origem de dados
     * @param eventPublisherSource  Origem de publicação de eventos
     * @return Produto salvo com identificadores atualizados
     */
    public static ProductResponse saveProduct(Long catalogId, ProductRequest productRequest, DataSource dataSource, EventPublisherSource eventPublisherSource) {

        var gateway = new CatalogGateway(dataSource);
        var eventPublisherGateway = new EventPublisherGateway(eventPublisherSource);

        var productInput = ProductMapper.toInput(productRequest);

        var catalog = AddProductToCategoryUseCase.addProductToCategory(catalogId, productInput, gateway);

        var savedCatalog = gateway.save(catalog);

        var savedProduct = savedCatalog.getLastProductOfCategory(productRequest.getCategoryId());

        var event = ProductCreatedEvent.of(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getPrice(),
                savedProduct.getCategoryId()
        );
        eventPublisherGateway.publishEvent(event);

        log.debug("Produto criado com sucesso: {}", savedProduct.getId());

        return ProductPresenter.toProductResponse(savedProduct);
    }
}