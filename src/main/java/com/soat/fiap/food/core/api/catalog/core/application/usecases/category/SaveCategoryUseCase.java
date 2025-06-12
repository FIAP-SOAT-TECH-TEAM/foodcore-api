package com.soat.fiap.food.core.api.catalog.core.application.usecases.category;

import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.requests.CategoryRequest;
import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.responses.CategoryResponse;
import com.soat.fiap.food.core.api.catalog.core.application.mapper.request.CategoryRequestMapper;
import com.soat.fiap.food.core.api.catalog.core.application.mapper.response.CategoryResponseMapper;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Salvar categoria.
 *
 */
@Slf4j
public class SaveCategoryUseCase {

    private final CategoryRequestMapper categoryRequestMapper;
    private final CategoryResponseMapper categoryResponseMapper;
    private final CatalogGateway catalogGateway;

    public SaveCategoryUseCase(
            CategoryRequestMapper categoryRequestMapper,
            CategoryResponseMapper categoryResponseMapper,
            CatalogGateway catalogGateway
    ) {
        this.categoryRequestMapper = categoryRequestMapper;
        this.categoryResponseMapper = categoryResponseMapper;
        this.catalogGateway = catalogGateway;
    }

    /**
     * Salva uma categoria.
     *
     * @param categoryRequest Categoria a ser salva
     * @return Categoria salva com identificadores atualizados
     */
    public CategoryResponse saveCategory(CategoryRequest categoryRequest) {

        var category = categoryRequestMapper.toDomain(categoryRequest);
        var catalog = catalogGateway.findById(categoryRequest.getCatalogId());

        log.debug("Criando categoria: {}", categoryRequest.getName());

        if (catalog.isEmpty()) {
            log.warn("Tentativa de cadastrar categoria com catalogo inexistente. ID catalogo: {}", categoryRequest.getCatalogId());
            throw new CatalogNotFoundException("Catalogo", categoryRequest.getCatalogId());
        }

        catalog.get().addCategory(category);

        var savedCatalog = catalogGateway.save(catalog.get());
        var savedCategory = savedCatalog.getCategories().getLast();

        var savedCategoryToResponse = categoryResponseMapper.toResponse(savedCategory);

        log.debug("Categoria criada com sucesso: {}", savedCategoryToResponse.getId());

        return savedCategoryToResponse;
    }
}
