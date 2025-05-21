package com.soat.fiap.food.core.api.catalog.domain.vo;

import java.util.Objects;

/**
 * Value Object que representa os detalhes de um produto ou categoria.
 */
public record Details(String name, String description) {

    /**
     * Construtor compacto que realiza validações dos campos.
     *
     * @param name        Nome do produto
     * @param description Descrição do produto
     * @throws NullPointerException     se {@code name} ou {@code description} forem nulos
     * @throws IllegalArgumentException se {@code name} ou {@code description} estiverem vazios,
     */
    public Details {
        Objects.requireNonNull(name, "Nome do produto não pode ser nulo");
        Objects.requireNonNull(description, "Descrição do produto não pode ser nula");

        String trimmedName = name.trim();
        String trimmedDescription = description.trim();

        if (trimmedName.isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode estar vazio");
        }
        if (trimmedName.length() > 100) {
            throw new IllegalArgumentException("Nome do produto deve ter no máximo 100 caracteres");
        }

        if (trimmedDescription.isEmpty()) {
            throw new IllegalArgumentException("Descrição do produto não pode estar vazia");
        }
        if (trimmedDescription.length() > 1000) {
            throw new IllegalArgumentException("Descrição do produto deve ter no máximo 1000 caracteres");
        }

        if (trimmedName.equalsIgnoreCase(trimmedDescription)) {
            throw new IllegalArgumentException("Nome e descrição não podem ser iguais");
        }
    }
}