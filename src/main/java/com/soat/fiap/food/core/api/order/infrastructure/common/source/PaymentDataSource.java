package com.soat.fiap.food.core.api.order.infrastructure.common.source;

import com.soat.fiap.food.core.api.order.core.application.outputs.payment.PaymentStatusOutput;

/**
 * DataSource para obtenção de Pagamento.
 */
public interface PaymentDataSource {

	/**
	 * Retorna o status de pagamento de um pedido pelo seu ID
	 *
	 * @param orderId
	 *            ID do pedido
	 */
	PaymentStatusOutput getOrderStatus(Long orderId);
}
