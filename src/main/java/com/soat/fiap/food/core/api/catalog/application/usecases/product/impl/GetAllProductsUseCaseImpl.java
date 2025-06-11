package com.soat.fiap.food.core.api.catalog.application.usecases.product.impl;

import com.soat.fiap.food.core.api.catalog.application.dto.response.ProductResponse;
import com.soat.fiap.food.core.api.catalog.application.mapper.response.ProductResponseMapper;
import com.soat.fiap.food.core.api.catalog.application.usecases.product.GetAllProductsUseCase;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.interfaces.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Caso de uso (implementação concreta): Obter todos produtos.
 *
 */
@Slf4j
public class GetAllProductsUseCaseImpl implements GetAllProductsUseCase {

    private final ProductResponseMapper productResponseMapper;
    private final CatalogGateway catalogGateway;

    public GetAllProductsUseCaseImpl(
            ProductResponseMapper productResponseMapper,
            CatalogGateway catalogRepository
    ) {
        this.productResponseMapper = productResponseMapper;
        this.catalogGateway = catalogRepository;
    }

    /**
     * Lista todos os produtos de uma categoria.
     *
     * @param catalogId ID do catálogo
     * @return Lista de produtos
     */
    @Override
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
