package com.soat.fiap.food.core.api.catalog.domain.vo;

import java.util.Objects;

public record ImageUrl(String value) {
    public ImageUrl {
        Objects.requireNonNull(value, "URL da imagem não pode ser nula");

        if (!value.matches("^[\\w\\-/]+\\.jpg$")) {
            throw new IllegalArgumentException("URL da imagem deve estar no formato 'pasta/nome-da-imagem.jpg'");
        }
        if (value.length() > 500) {
            throw new IllegalArgumentException("Url da imagem deve ter no máximo 500 caracteres");
        }
    }
}

