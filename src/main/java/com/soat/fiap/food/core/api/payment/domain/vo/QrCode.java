package com.soat.fiap.food.core.api.payment.domain.vo;

import java.util.Objects;

/**
 * Value Object que representa a URL de uma imagem de um produto ou categoria.
 */
public record QrCode(String value) {

    /**
     * Construtor que cria uma nova instância de QrCode com os dados fornecidos.
     *
     * @param Data do QrCode
     * @throws NullPointerException se o conteúdo do QrCode for nulo
     * @throws IllegalArgumentException se a data do QrCode ultrapassar o tamanho permitido
     */
    public QrCode {
        Objects.requireNonNull(value, "QrCode não pode ser nulo");

        if (value.length() > 255) {
            throw new IllegalArgumentException("O conteúdo do QrCode não pode ultrapassar 255 caracteres");
        }
    }
}