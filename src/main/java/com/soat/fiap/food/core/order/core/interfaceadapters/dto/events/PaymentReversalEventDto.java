package com.soat.fiap.food.core.order.core.interfaceadapters.dto.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * DTO utilizado para representar dados do evento de domínio
 * PaymentReversalEvent. Serve como objeto de transferência entre o
 * domínio e o mundo externo (DataSource).
 */
@Data
public class PaymentReversalEventDto {
	private final Long orderId;
}
