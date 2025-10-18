package com.soat.fiap.food.core.order.core.interfaceadapters.dto.events;

import lombok.Data;

/**
 * DTO utilizado para representar dados do evento de domínio
 * PaymentInitializationErrorEvent. Serve como objeto de transferência entre o
 * domínio e o mundo externo (DataSource).
 */
@Data
public class PaymentInitializationErrorEventDto {
	public Long orderId;
	public String errorMessage;
}
