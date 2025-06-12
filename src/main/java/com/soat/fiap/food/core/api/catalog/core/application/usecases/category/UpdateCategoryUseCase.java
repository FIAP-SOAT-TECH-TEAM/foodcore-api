package com.soat.fiap.food.core.api.catalog.core.application.usecases.category;

import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.requests.CategoryRequest;
import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.responses.CategoryResponse;
import com.soat.fiap.food.core.api.catalog.core.application.mapper.request.CategoryRequestMapper;
import com.soat.fiap.food.core.api.catalog.core.application.mapper.response.CategoryResponseMapper;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Atualizar categoria.
 *
 */
@Slf4j
public class UpdateCategoryUseCase {

    private final CategoryRequestMapper categoryRequestMapper;
    private final CategoryResponseMapper categoryResponseMapper;
    private final CatalogGateway catalogGateway;

    public UpdateCategoryUseCase(
            CategoryRequestMapper categoryRequestMapper,
            CategoryResponseMapper categoryResponseMapper,
            CatalogGateway catalogGateway
    ) {
        this.categoryRequestMapper = categoryRequestMapper;
        this.categoryResponseMapper = categoryResponseMapper;
        this.catalogGateway = catalogGateway;
    }

    /**
     * Atualiza uma categoria.
     *
     * @param categoryRequest Categoria a ser atualizada
     * @return Categoria atualizada com identificadores atualizados
     */
    public CategoryResponse updateCategory(Long catalogId, Long categoryId, CategoryRequest categoryRequest) {

        var category = categoryRequestMapper.toDomain(categoryRequest);
        var existingCatalog = catalogGateway.findById(catalogId);
        category.setId(categoryId);

        log.debug("Atualizando categoria: {}", categoryId);

        if (existingCatalog.isEmpty()) {
            log.warn("Tentativa de atualizar categoria com catálogo inexistente. Id: {}", catalogId);
            throw new CatalogNotFoundException("Catalogo", catalogId);
        }
        else if (!categoryRequest.getCatalogId().equals(catalogId)) {

            var newCatalog = catalogGateway.findById(categoryRequest.getCatalogId());

            if (newCatalog.isEmpty()) {
                log.warn("Tentativa de mover categoria para catálogo inexistente. Id: {}", categoryRequest.getCatalogId());
                throw new CatalogNotFoundException("Catalogo", categoryRequest.getCatalogId());
            }

            existingCatalog.get().moveCatalogCategory(newCatalog.get(), categoryId);
            catalogGateway.save(existingCatalog.get());
            log.debug("Categoria movida com sucesso para catálogo: {}", categoryRequest.getCatalogId());

            newCatalog.get().updateCategory(category);
            var updatedCatalog = catalogGateway.save(newCatalog.get());
            var updatedCategory = updatedCatalog.getCategoryById(categoryId);

            var updatedCategoryToResponse = categoryResponseMapper.toResponse(updatedCategory);

            log.debug("Categoria atualizada com sucesso: {}", catalogId);

            return updatedCategoryToResponse;
        }

        existingCatalog.get().updateCategory(category);

        var updatedCatalog = catalogGateway.save(existingCatalog.get());
        var updatedCategory = updatedCatalog.getCategoryById(categoryId);

        var updatedCategoryToResponse = categoryResponseMapper.toResponse(updatedCategory);

        log.debug("Categoria atualizada com sucesso: {}", catalogId);

        return updatedCategoryToResponse;
    }
}
