package com.soat.fiap.food.core.api.catalog.application.usecases.category.impl;

import com.soat.fiap.food.core.api.catalog.application.dto.response.CategoryResponse;
import com.soat.fiap.food.core.api.catalog.application.mapper.response.CategoryResponseMapper;
import com.soat.fiap.food.core.api.catalog.application.usecases.category.GetAllCategoriesUseCase;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.interfaces.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Caso de uso (implementação concreta): Obter todas categorias.
 *
 */
@Slf4j
public class GetAllCategoriesUseCaseImpl implements GetAllCategoriesUseCase {

    private final CategoryResponseMapper categoryResponseMapper;
    private final CatalogGateway catalogGateway;

    public GetAllCategoriesUseCaseImpl(
            CategoryResponseMapper categoryResponseMapper,
            CatalogGateway catalogRepository
    ) {
        this.categoryResponseMapper = categoryResponseMapper;
        this.catalogGateway = catalogRepository;
    }

    /**
     * Lista todas as categorias de um catálogo.
     *
     * @param catalogId ID do catálogo
     * @return Lista de categorias
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories(Long catalogId) {
        log.debug("Buscando todas as categorias do catalogo de id: {}", catalogId);

        var catalog = catalogGateway.findById(catalogId);

        if (catalog.isEmpty()) {
            log.warn("Catalogo não encontrado. Id: {}", catalogId);
            throw new CatalogNotFoundException("Catalogo", catalogId);
        }

        var categories = catalog.get().getCategories();

        log.debug("Encontradas {} categorias", categories.size());

        return categoryResponseMapper.toResponseList(categories);
    }
}
