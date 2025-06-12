package com.soat.fiap.food.core.api.catalog.application.usecases.product;

import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.interfaces.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Remover produto pelo seu identificador.
 *
 */
@Slf4j
public class DeleteProductUseCase {

    private final CatalogGateway catalogGateway;

    public DeleteProductUseCase(
            CatalogGateway catalogGateway
    ) {
        this.catalogGateway = catalogGateway;
    }

    /**
     * Exclui um produto de uma categoria.
     *
     * @param catalogId  ID do catálogo
     * @param categoryId ID da categoria
     * @param productId  ID do produto a ser removido
     */
    public void deleteProduct(Long catalogId, Long categoryId, Long productId) {
        log.debug("Excluindo produto de id: {} da categoria de id: {} do catalogo de id: {}", productId, categoryId, catalogId);

        var catalog = catalogGateway.findById(catalogId);

        if (catalog.isEmpty()) {
            log.warn("Tentativa de excluir produto com catálogo inexistente. Id: {}", catalogId);
            throw new CatalogNotFoundException("Catalogo", catalogId);
        }

        catalog.get().removeProductFromCategory(categoryId, productId);

        catalogGateway.save(catalog.get());

        log.debug("Produto excluído com sucesso: {}", productId);
    }
}
