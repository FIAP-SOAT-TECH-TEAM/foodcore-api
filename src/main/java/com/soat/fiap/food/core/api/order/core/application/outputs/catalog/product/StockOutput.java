package com.soat.fiap.food.core.api.order.core.application.outputs.catalog.product;

import java.time.LocalDateTime;

/**
 * Representa o output (Application Layer) do estoque de um produto.
 *
 * @param id
 *            Identificador único do estoque.
 * @param quantity
 *            Quantidade disponível em estoque.
 * @param createdAt
 *            Data e hora de criação do registro de estoque.
 * @param updatedAt
 *            Data e hora da última atualização do registro de estoque.
 */
public record StockOutput(Long id, Integer quantity, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
