package com.soat.fiap.food.core.api.catalog.application.usecases.product.impl;

import com.soat.fiap.food.core.api.catalog.application.dto.response.ProductResponse;
import com.soat.fiap.food.core.api.catalog.application.mapper.response.ProductResponseMapper;
import com.soat.fiap.food.core.api.catalog.application.usecases.product.GetProductByIdUseCase;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.interfaces.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * Caso de uso (implementação concreta): Obter produto por identificador.
 *
 */
@Slf4j
public class GetProductByIdUseCaseImpl implements GetProductByIdUseCase {

    private final ProductResponseMapper productResponseMapper;
    private final CatalogGateway catalogGateway;

    public GetProductByIdUseCaseImpl(
            ProductResponseMapper productResponseMapper,
            CatalogGateway catalogRepository
    ) {
        this.productResponseMapper = productResponseMapper;
        this.catalogGateway = catalogRepository;
    }

    /**
     * Busca um produto por ID dentro de uma categoria.
     *
     * @param catalogId  ID do catálogo
     * @param categoryId ID da categoria
     * @param productId  ID do produto
     * @return Produto encontrado
     */
    @Override
    @Transactional(readOnly = true)
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
