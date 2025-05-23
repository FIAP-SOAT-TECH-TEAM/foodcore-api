package com.soat.fiap.food.core.api.catalog.domain.vo;

import java.util.Objects;

/**
 * Value Object que representa a URL de uma imagem de um produto ou categoria.
 */
public record ImageUrl(String imageUrl) {

    /**
     * Construtor compacto que valida a URL da imagem.
     *
     * @param imageUrl caminho da imagem relativo ao repositório de imagens
     * @throws NullPointerException se a URL for nula
     * @throws IllegalArgumentException se o formato da URL for inválido ou ultrapassar o tamanho permitido
     */
    public ImageUrl {
        Objects.requireNonNull(imageUrl, "URL da imagem não pode ser nula");

        if (!imageUrl.matches("^[\\w\\-/]+\\.jpg$")) {
            throw new IllegalArgumentException("URL da imagem deve estar no formato 'pasta/nome-da-imagem.jpg'");
        }
        if (imageUrl.length() > 500) {
            throw new IllegalArgumentException("Url da imagem deve ter no máximo 500 caracteres");
        }
    }
}