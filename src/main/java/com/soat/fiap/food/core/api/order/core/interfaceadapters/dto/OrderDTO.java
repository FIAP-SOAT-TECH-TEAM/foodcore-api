package com.soat.fiap.food.core.api.order.core.interfaceadapters.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.soat.fiap.food.core.api.order.core.domain.vo.OrderStatus;

/**
 * DTO que representa os dados de um pedido na comunicação entre camadas.
 */
public record OrderDTO(Long id, Long userId, String orderNumber, OrderStatus status, BigDecimal amount,
		List<OrderItemDTO> items, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
