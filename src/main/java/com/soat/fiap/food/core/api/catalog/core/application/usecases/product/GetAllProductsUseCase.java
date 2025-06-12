package com.soat.fiap.food.core.api.catalog.core.application.usecases.product;

import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.responses.ProductResponse;
import com.soat.fiap.food.core.api.catalog.core.application.mapper.response.ProductResponseMapper;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Caso de uso: Obter todos produtos.
 *
 */
@Slf4j
public class GetAllProductsUseCase {

    private final ProductResponseMapper productResponseMapper;
    private final CatalogGateway catalogGateway;

    public GetAllProductsUseCase(
            ProductResponseMapper productResponseMapper,
            CatalogGateway catalogGateway
    ) {
        this.productResponseMapper = productResponseMapper;
        this.catalogGateway = catalogGateway;
    }

    /**
     * Lista todos os produtos de uma categoria.
     *
     * @param catalogId ID do catálogo
     * @return Lista de produtos
     */
    public List<ProductResponse> getAllProducts(Long catalogId, Long categoryId) {
        log.debug("Buscando todos os produtos da categoria de id: {}", categoryId);

        var catalog = catalogGateway.findById(catalogId);

        if (catalog.isEmpty()) {
            log.warn("Catalogo não encontrado. Id: {}", catalogId);
            throw new CatalogNotFoundException("Catalogo", catalogId);
        }

        var products = catalog.get().getProductsFromCategoryById(categoryId);

        log.debug("Encontrados {} produtos", products.size());

        return productResponseMapper.toResponseList(products);
    }
}
