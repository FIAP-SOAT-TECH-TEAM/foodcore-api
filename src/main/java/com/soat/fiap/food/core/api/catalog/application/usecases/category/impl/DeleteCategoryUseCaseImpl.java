package com.soat.fiap.food.core.api.catalog.application.usecases.category.impl;

import com.soat.fiap.food.core.api.catalog.application.usecases.category.DeleteCategoryUseCase;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.interfaces.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * Caso de uso (implementação concreta): Remover categoria pelo seu identificador.
 *
 */
@Slf4j
public class DeleteCategoryUseCaseImpl implements DeleteCategoryUseCase {

    private final CatalogGateway catalogGateway;

    public DeleteCategoryUseCaseImpl(
            CatalogGateway catalogRepository
    ) {
        this.catalogGateway = catalogRepository;
    }

    /**
     * Exclui uma categoria de um catálogo.
     *
     * @param catalogId  ID do catálogo
     * @param categoryId ID da categoria
     */
    @Override
    @Transactional
    public void deleteCategory(Long catalogId, Long categoryId) {
        log.debug("Excluindo categoria de id: {} do catalogo de id: {}", categoryId, catalogId);

        var catalog = catalogGateway.findById(catalogId);

        if (catalog.isEmpty()) {
            log.warn("Tentativa de excluir categoria com catálogo inexistente. Id: {}", catalogId);
            throw new CatalogNotFoundException("Catalogo", catalogId);
        }

        catalog.get().removeCategory(categoryId, true);

        catalogGateway.save(catalog.get());

        log.debug("Categoria excluída com sucesso: {}", categoryId);
    }
}
