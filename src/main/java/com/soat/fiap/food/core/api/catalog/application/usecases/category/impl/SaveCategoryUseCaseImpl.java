package com.soat.fiap.food.core.api.catalog.application.usecases.category.impl;

import com.soat.fiap.food.core.api.catalog.application.dto.request.CategoryRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.response.CategoryResponse;
import com.soat.fiap.food.core.api.catalog.application.mapper.request.CategoryRequestMapper;
import com.soat.fiap.food.core.api.catalog.application.mapper.response.CategoryResponseMapper;
import com.soat.fiap.food.core.api.catalog.application.usecases.category.SaveCategoryUseCase;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.interfaces.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso (implementação concreta): Salvar categoria.
 *
 */
@Slf4j
public class SaveCategoryUseCaseImpl implements SaveCategoryUseCase {

    private final CategoryRequestMapper categoryRequestMapper;
    private final CategoryResponseMapper categoryResponseMapper;
    private final CatalogGateway catalogGateway;

    public SaveCategoryUseCaseImpl(
            CategoryRequestMapper categoryRequestMapper,
            CategoryResponseMapper categoryResponseMapper,
            CatalogGateway catalogRepository
    ) {
        this.categoryRequestMapper = categoryRequestMapper;
        this.categoryResponseMapper = categoryResponseMapper;
        this.catalogGateway = catalogRepository;
    }

    /**
     * Salva uma categoria.
     *
     * @param categoryRequest Categoria a ser salva
     * @return Categoria salva com identificadores atualizados
     */
    @Override
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
