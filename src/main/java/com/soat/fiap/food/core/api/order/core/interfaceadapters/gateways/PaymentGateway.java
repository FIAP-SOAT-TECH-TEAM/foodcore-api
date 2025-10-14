package com.soat.fiap.food.core.api.order.core.interfaceadapters.gateways;

import com.soat.fiap.food.core.api.order.core.application.outputs.payment.PaymentStatusOutput;
import com.soat.fiap.food.core.api.order.infrastructure.common.source.PaymentDataSource;

/**
 * Gateway para comunicação com o microsserviço de Pagamento
 */
public class PaymentGateway {

	private final PaymentDataSource paymentDataSource;

	public PaymentGateway(PaymentDataSource paymentDataSource) {
		this.paymentDataSource = paymentDataSource;
	}

	/**
	 * Retorna o status de pagamento de um pedido pelo seu ID
	 *
	 * @param orderId
	 *            ID do pedido
	 */
	public PaymentStatusOutput getOrderStatus(Long orderId) {
		return paymentDataSource.getOrderStatus(orderId);
	}

}
