package com.soat.fiap.food.core.api.order.infrastructure.out.catalog.product.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * Representa a entidade de Stock na API do microsserviço de catálogo.
 */
@Data
public class StockEntity {
	private Long id;

	private Integer quantity;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
