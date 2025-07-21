package com.soat.fiap.food.core.api.order.core.interfaceadapters.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO que representa um item de pedido.
 */
public record OrderItemDTO(Long id, Long productId, String name, int quantity, BigDecimal price, String observations,
		LocalDateTime createdAt, LocalDateTime updatedAt) {

}
