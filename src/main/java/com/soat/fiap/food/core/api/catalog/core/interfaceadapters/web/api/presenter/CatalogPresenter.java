package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.web.api.presenter;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.responses.CatalogResponse;

public class CatalogPresenter {

    public static CatalogResponse toCatalogResponse(Catalog catalog) {
        return new CatalogResponse(
                catalog.getId(),
                catalog.getName(),
                catalog.getCreatedAt(),
                catalog.getUpdatedAt()
        );
    }
}
