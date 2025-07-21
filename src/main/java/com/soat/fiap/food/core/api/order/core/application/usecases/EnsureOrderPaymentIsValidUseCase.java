package com.soat.fiap.food.core.api.order.core.application.usecases;

import com.soat.fiap.food.core.api.order.core.domain.exceptions.OrderNotFoundException;
import com.soat.fiap.food.core.api.order.core.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.gateways.OrderGateway;
import com.soat.fiap.food.core.api.payment.core.domain.exceptions.PaymentNotFoundException;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.PaymentGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Garantir que o pagamento do pedido seja válido.
 */
@Slf4j
public class EnsureOrderPaymentIsValidUseCase {

	/**
	 * Valida se o pagamento existe para o pedido, exceto quando o status for
	 * RECEIVED.
	 *
	 * @param id
	 *            ID do Pedido a ser validado
	 * @param paymentGateway
	 *            Gateway de pagamento para comunicação com o mundo exterior
	 * @param orderGateway
	 *            Gateway de pedido para comunicação com o mundo exterior
	 * @throws PaymentNotFoundException
	 *             se o pagamento não existir
	 * @throws OrderNotFoundException
	 *             se o pedido não existir
	 */
	public static void ensureOrderPaymentIsValid(Long id, PaymentGateway paymentGateway, OrderGateway orderGateway) {

		var order = orderGateway.findById(id);
		var payment = paymentGateway.findTopByOrderIdOrderByIdDesc(id);

		if (order.isEmpty()) {
			throw new OrderNotFoundException("Pedido", id);
		} else if (payment.isEmpty() && order.get().getOrderStatus() != OrderStatus.RECEIVED) {
			throw new PaymentNotFoundException("O pagamento do pedido não existe");
		}
	}
}
