package com.soat.fiap.food.core.api.order.core.application.inputs;

import java.util.List;

/**
 * Representa um DTO de entrada da aplicação (Application Layer)
 * contendo os dados necessários para criar um novo pedido.
 */
public record CreateOrderInput(
        Long userId,
        List<OrderItemInput> items
) {
    /**
     * Construtor do record {@code CreateOrderInput}.
     *
     * @param userId ID do cliente (opcional).
     * @param items  Lista de itens do pedido.
     */
    public CreateOrderInput {
    }
}