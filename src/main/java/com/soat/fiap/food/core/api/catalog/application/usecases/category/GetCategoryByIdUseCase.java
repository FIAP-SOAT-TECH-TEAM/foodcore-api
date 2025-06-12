package com.soat.fiap.food.core.api.catalog.application.usecases.category;

import com.soat.fiap.food.core.api.catalog.application.dto.response.CategoryResponse;
import com.soat.fiap.food.core.api.catalog.application.mapper.response.CategoryResponseMapper;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.interfaces.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Obter categoria por identificador.
 *
 */
@Slf4j
public class GetCategoryByIdUseCase{

    private final CategoryResponseMapper categoryResponseMapper;
    private final CatalogGateway catalogGateway;

    public GetCategoryByIdUseCase(
            CategoryResponseMapper categoryResponseMapper,
            CatalogGateway catalogGateway
    ) {
        this.categoryResponseMapper = categoryResponseMapper;
        this.catalogGateway = catalogGateway;
    }

    /**
     * Busca uma categoria por ID dentro de um catálogo.
     *
     * @param catalogId  ID do catálogo
     * @param categoryId ID da categoria
     * @return Categoria encontrada
     */
    public CategoryResponse getCategoryById(Long catalogId, Long categoryId) {
        log.debug("Buscando categoria de id: {} no catalogo de id: {}", categoryId, catalogId);

        var catalog = catalogGateway.findById(catalogId);

        if (catalog.isEmpty()) {
            log.warn("Catalogo não encontrado. Id: {}", catalogId);
            throw new CatalogNotFoundException("Catalogo", catalogId);
        }

        var category = catalog.get().getCategoryById(categoryId);

        return categoryResponseMapper.toResponse(category);
    }
}
