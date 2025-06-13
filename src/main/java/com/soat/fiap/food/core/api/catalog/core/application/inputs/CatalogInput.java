package com.soat.fiap.food.core.api.catalog.core.application.inputs;

import lombok.Getter;

/**
 * Representa um DTO de entrada da aplicação (Application Layer)
 * contendo os dados necessários para criar ou manipular um catálogo.
 *
 */
@Getter
public class CatalogInput {

    /** Nome do catálogo */
    private final String name;

    /**
     * Construtor para inicializar um {@code CatalogInput} com o nome do catálogo.
     *
     * @param name O nome do catálogo.
     */
    public CatalogInput(String name) {
        this.name = name;
    }
}