package com.soat.fiap.food.core.api.payment.core.application.usecases;

import com.soat.fiap.food.core.api.order.core.domain.exceptions.OrderNotFoundException;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.AcquirerGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Obter os detalhes de um pedido registrado no adquirente.
 */
@Slf4j
public class GetAcquirerOrderUseCase {

	/**
	 * Executa a consulta de um pedido no adquirente, com base no seu identificador.
	 *
	 * @param orderId
	 *            ID do pedido no adquirente.
	 * @param gateway
	 *            Gateway responsável por acessar o adquirente.
	 * @return Objeto {@link Object} bruto com os dados do pedido.
	 * @throws OrderNotFoundException
	 *             caso o pedido não seja encontrado no adquirente.
	 */
	public static Object getAcquirerOrder(Long orderId, AcquirerGateway gateway) {
		var order = gateway.getAcquirerOrder(orderId);

		if (order == null) {
			log.warn("Pedido não foi encontrado no adquirente! Merchant Order: {}", orderId);
			throw new OrderNotFoundException("Pedido adquirente", orderId);
		}

		return order;
	}
}
