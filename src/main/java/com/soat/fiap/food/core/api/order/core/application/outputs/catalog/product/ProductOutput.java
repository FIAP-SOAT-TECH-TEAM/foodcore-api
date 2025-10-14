package com.soat.fiap.food.core.api.order.core.application.outputs.catalog.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa um output (Application Layer) de produto, contendo informações
 * essenciais para exibição e lógica de aplicação.
 *
 * @param id
 *            Identificador único do produto.
 * @param name
 *            Nome do produto.
 * @param description
 *            Descrição detalhada do produto.
 * @param imageUrl
 *            URL da imagem representativa do produto.
 * @param price
 *            Preço do produto.
 * @param stock
 *            Informações de estoque do produto.
 * @param displayOrder
 *            Ordem de exibição do produto.
 * @param active
 *            Indica se o produto está ativo.
 * @param categoryIsActive
 *            Indica se a categoria do produto está ativa.
 * @param createdAt
 *            Data e hora de criação do produto.
 * @param updatedAt
 *            Data e hora da última atualização do produto.
 */
public record ProductOutput(Long id, String name, String description, String imageUrl, BigDecimal price,
		StockOutput stock, Integer displayOrder, boolean active, boolean categoryIsActive, LocalDateTime createdAt,
		LocalDateTime updatedAt) {
}
