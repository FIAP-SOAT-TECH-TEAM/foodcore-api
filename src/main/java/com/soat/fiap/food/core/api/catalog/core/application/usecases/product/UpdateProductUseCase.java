package com.soat.fiap.food.core.api.catalog.core.application.usecases.product;

import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.requests.ProductRequest;
import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.responses.ProductResponse;
import com.soat.fiap.food.core.api.catalog.core.application.mapper.request.ProductRequestMapper;
import com.soat.fiap.food.core.api.catalog.core.application.mapper.response.ProductResponseMapper;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Atualizar produto.
 *
 */
@Slf4j
public class UpdateProductUseCase {

    private final ProductRequestMapper productRequestMapper;
    private final ProductResponseMapper productResponseMapper;
    private final CatalogGateway catalogGateway;

    public UpdateProductUseCase(
            ProductRequestMapper productRequestMapper,
            ProductResponseMapper productResponseMapper,
            CatalogGateway catalogGateway
    ) {
        this.productRequestMapper = productRequestMapper;
        this.productResponseMapper = productResponseMapper;
        this.catalogGateway = catalogGateway;
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
