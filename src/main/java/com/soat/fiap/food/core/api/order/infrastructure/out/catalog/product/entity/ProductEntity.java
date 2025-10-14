package com.soat.fiap.food.core.api.order.infrastructure.out.catalog.product.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa a entidade de Product na API do microsserviço de catálogo.
 */
@Data
public class ProductEntity {

	private Long id;

	private String name;

	private String description;

	private BigDecimal price;

	private String imageUrl;

	private boolean active;

	private Integer displayOrder;

	private StockEntity stock;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
