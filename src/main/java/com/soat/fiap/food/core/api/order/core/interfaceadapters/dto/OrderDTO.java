package com.soat.fiap.food.core.api.order.core.interfaceadapters.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.soat.fiap.food.core.api.order.core.domain.vo.OrderStatus;

/**
 * DTO que representa os dados de um pedido na comunicação entre camadas.
 */
public record OrderDTO(Long id, Long userId, OrderStatus status, List<OrderItemDTO> items, LocalDateTime createdAt,
		LocalDateTime updatedAt) {
}
