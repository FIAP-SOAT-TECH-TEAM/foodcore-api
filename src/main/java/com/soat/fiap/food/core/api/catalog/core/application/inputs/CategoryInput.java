package com.soat.fiap.food.core.api.catalog.core.application.inputs;

import lombok.Getter;

/**
 * Representa um DTO de entrada da aplicação (Application Layer)
 * contendo os dados necessários para criar ou manipular uma categoria.
 */
@Getter
public class CategoryInput {

    /** ID do catálogo ao qual a categoria pertence */
    private final Long catalogId;

    /** Nome da categoria */
    private final String name;

    /** Descrição da categoria */
    private final String description;

    /** Status da categoria */
    private final boolean active;

    /** Ordem de exibição da categoria */
    private final Integer displayOrder;

    /**
     * Construtor para inicializar um {@code CategoryInput} com os dados necessários.
     *
     * @param catalogId     ID do catálogo
     * @param name          Nome da categoria
     * @param description   Descrição da categoria
     * @param active        Status da categoria
     * @param displayOrder  Ordem de exibição da categoria
     */
    public CategoryInput(Long catalogId, String name, String description, boolean active, Integer displayOrder) {
        this.catalogId = catalogId;
        this.name = name;
        this.description = description;
        this.active = active;
        this.displayOrder = displayOrder;
    }
}
