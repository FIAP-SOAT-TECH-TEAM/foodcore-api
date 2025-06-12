package com.soat.fiap.food.core.api.catalog.core.application.usecases.product;

import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.responses.ProductResponse;
import com.soat.fiap.food.core.api.catalog.core.application.mapper.response.ProductResponseMapper;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Obter produto por identificador.
 *
 */
@Slf4j
public class GetProductByIdUseCase {

    private final ProductResponseMapper productResponseMapper;
    private final CatalogGateway catalogGateway;

    public GetProductByIdUseCase(
            ProductResponseMapper productResponseMapper,
            CatalogGateway catalogGateway
    ) {
        this.productResponseMapper = productResponseMapper;
        this.catalogGateway = catalogGateway;
    }

    /**
     * Busca um produto por ID dentro de uma categoria.
     *
     * @param catalogId  ID do catálogo
     * @param categoryId ID da categoria
     * @param productId  ID do produto
     * @return Produto encontrado
     */
    public ProductResponse getProductById(Long catalogId, Long categoryId, Long productId) {
        log.debug("Buscando produto de id: {} na categoria de id: {} no catalogo de id: {}", productId, categoryId, catalogId);

        var catalog = catalogGateway.findById(catalogId);

        if (catalog.isEmpty()) {
            log.warn("Catalogo não encontrado. Id: {}", catalogId);
            throw new CatalogNotFoundException("Catalogo", catalogId);
        }

        var product = catalog.get().getProductFromCategoryById(categoryId, productId);

        return productResponseMapper.toResponse(product);
    }
}
