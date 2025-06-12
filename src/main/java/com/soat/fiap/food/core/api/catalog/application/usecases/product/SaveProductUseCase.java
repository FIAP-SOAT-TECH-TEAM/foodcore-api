package com.soat.fiap.food.core.api.catalog.application.usecases.product;

import com.soat.fiap.food.core.api.catalog.application.dto.request.ProductRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.response.ProductResponse;
import com.soat.fiap.food.core.api.catalog.application.mapper.request.ProductRequestMapper;
import com.soat.fiap.food.core.api.catalog.application.mapper.response.ProductResponseMapper;
import com.soat.fiap.food.core.api.catalog.domain.events.ProductCreatedEvent;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.interfaces.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.interfaces.gateways.EventPublisherGateway;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso (implementação concreta): Salvar produto.
 *
 */
@Slf4j
public class SaveProductUseCase {

    private final ProductRequestMapper productRequestMapper;
    private final ProductResponseMapper productResponseMapper;
    private final CatalogGateway catalogGateway;
    private final EventPublisherGateway eventPublisherGateway;

    public SaveProductUseCase(
            ProductRequestMapper productRequestMapper,
            ProductResponseMapper productResponseMapper,
            CatalogGateway catalogGateway,
            EventPublisherGateway eventPublisherGateway
    ) {
        this.productRequestMapper = productRequestMapper;
        this.productResponseMapper = productResponseMapper;
        this.catalogGateway = catalogGateway;
        this.eventPublisherGateway = eventPublisherGateway;
    }

    /**
     * Salva um produto.
     *
     * @param catalogId  ID do catálogo ao qual a categoria pertence
     * @param productRequest Produto a ser salvo
     * @return Produto salvo com identificadores atualizados
     */
    public ProductResponse saveProduct(Long catalogId, ProductRequest productRequest) {
        log.debug("Criando produto: {}", productRequest.getName());

        var catalog = catalogGateway.findById(catalogId);
        var product = productRequestMapper.toDomain(productRequest);

        if (catalog.isEmpty()) {
            log.warn("Tentativa de cadastrar produto em catálogo inexistente. Id: {}", catalogId);
            throw new CatalogNotFoundException("Catalogo", catalogId);
        }

        catalog.get().addProductToCategory(productRequest.getCategoryId(), product);

        var savedCatalog = catalogGateway.save(catalog.get());
        var savedProduct = savedCatalog.getLastProductOfCategory(productRequest.getCategoryId());

        var productResponse = productResponseMapper.toResponse(savedProduct);

        ProductCreatedEvent event = ProductCreatedEvent.of(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getPrice(),
                savedProduct.getCategoryId()
        );
        eventPublisherGateway.publishEvent(event);

        log.debug("Produto criado com sucesso: {}", productResponse.getId());

        return productResponse;
    }
}
