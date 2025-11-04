package com.soat.fiap.food.core.order.core.domain.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * Evento de domínio (DDD) emitido quando um pedido está pronto.
 */
@Data
public class OrderReadyEvent {
	private String clientId;
	private String orderNumber;
	private BigDecimal amount;
	private final LocalDateTime readyAt = LocalDateTime.now();
}
