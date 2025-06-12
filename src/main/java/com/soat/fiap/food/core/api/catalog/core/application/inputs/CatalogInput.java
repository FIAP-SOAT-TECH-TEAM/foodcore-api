package com.soat.fiap.food.core.api.catalog.core.application.inputs;

import lombok.Getter;

@Getter
public class CatalogInput {

    private final String name;

    public CatalogInput(String name) {
        this.name = name;
    }
}

