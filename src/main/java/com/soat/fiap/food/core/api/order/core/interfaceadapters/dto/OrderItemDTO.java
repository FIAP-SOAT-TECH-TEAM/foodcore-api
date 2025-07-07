package com.soat.fiap.food.core.api.order.core.interfaceadapters.dto;

import java.math.BigDecimal;

/**
 * DTO que representa um item de pedido.
 */
public record OrderItemDTO(Long productId, String name, int quantity, BigDecimal price, String observations) {
}
