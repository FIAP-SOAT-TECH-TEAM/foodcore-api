package com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.CatalogInput;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.requests.CatalogRequest;

public class CatalogMapper {
    public static CatalogInput toInput(CatalogRequest request) {
        return new CatalogInput(request.getName());
    }

    public static Catalog toDomain(CatalogInput input) {
        return new Catalog(input.getName());
    }
}
