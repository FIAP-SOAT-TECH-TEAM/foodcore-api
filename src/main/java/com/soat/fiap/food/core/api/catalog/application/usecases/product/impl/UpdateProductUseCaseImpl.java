package com.soat.fiap.food.core.api.catalog.application.usecases.product.impl;

import com.soat.fiap.food.core.api.catalog.application.dto.request.ProductRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.response.ProductResponse;
import com.soat.fiap.food.core.api.catalog.application.mapper.request.ProductRequestMapper;
import com.soat.fiap.food.core.api.catalog.application.mapper.response.ProductResponseMapper;
import com.soat.fiap.food.core.api.catalog.application.usecases.product.UpdateProductUseCase;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.interfaces.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * Caso de uso (implementação concreta): Atualizar produto.
 *
 */
@Slf4j
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {

    private final ProductRequestMapper productRequestMapper;
    private final ProductResponseMapper productResponseMapper;
    private final CatalogGateway catalogGateway;

    public UpdateProductUseCaseImpl(
            ProductRequestMapper productRequestMapper,
            ProductResponseMapper productResponseMapper,
            CatalogGateway catalogRepository
    ) {
        this.productRequestMapper = productRequestMapper;
        this.productResponseMapper = productResponseMapper;
        this.catalogGateway = catalogRepository;
    }

    /**
     * Atualiza um produto.
     *
     * @param catalogId  ID do catálogo
     * @param categoryId ID da categoria
     * @param productId  ID do produto a ser atualizado
     * @param productRequest Produto com os dados atualizados
     * @return Produto atualizado com identificadores atualizados
     */
    @Override
    @Transactional
    public ProductResponse updateProduct(Long catalogId, Long categoryId, Long productId, ProductRequest productRequest) {
        log.debug("Atualizando produto: {}", productId);

        var catalog = catalogGateway.findById(catalogId);
        var product = productRequestMapper.toDomain(productRequest);
        product.setId(productId);

        if (catalog.isEmpty()) {
            log.warn("Tentativa de atualizar produto com catálogo inexistente. Id: {}", catalogId);
            throw new CatalogNotFoundException("Catalogo", catalogId);
        }
        else if (!productRequest.getCategoryId().equals(categoryId)) {

            catalog.get().moveCategoryProduct(categoryId, productRequest.getCategoryId(), productId);

            log.debug("Produto movido com sucesso para categoria: {}", productRequest.getCategoryId());
        }

        catalog.get().updateProductInCategory(productRequest.getCategoryId(), product);

        var updatedCatalog = catalogGateway.save(catalog.get());
        var updatedProduct = updatedCatalog.getProductFromCategoryById(productRequest.getCategoryId(), productId);

        var productResponse = productResponseMapper.toResponse(updatedProduct);

        log.debug("Produto atualizado com sucesso: {}", productId);

        return productResponse;
    }
}
