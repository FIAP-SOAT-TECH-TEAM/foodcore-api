package com.soat.fiap.food.core.api.order.core.application.usecases;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.soat.fiap.food.core.api.order.core.domain.exceptions.OrderException;
import com.soat.fiap.food.core.api.order.core.domain.model.Order;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.AuthenticatedUserGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Aplicar desconto baseado no tempo de cadastro do cliente.
 */
@Slf4j
public class ApplyDiscountUseCase {

	/**
	 * Aplica um desconto no pedido baseado no tempo de cadastro do cliente.
	 * <p>
	 * O desconto é calculado como 2% ao ano desde o ano de criação do usuário até o
	 * ano atual. O desconto máximo aplicado será menor que 96%. Caso o cliente não
	 * seja encontrado, uma exceção {@link OrderException} é lançada.
	 *
	 * @param order
	 *            pedido no qual o desconto será aplicado
	 * @param authenticatedUserGateway
	 *            Gateway de usuário autenticado
	 * @throws OrderException
	 *             se o cliente do pedido não for encontrado
	 */
	public static void applyDiscount(Order order, AuthenticatedUserGateway authenticatedUserGateway) {

		var createDate = authenticatedUserGateway.getCreationDate();

		if (createDate == null) {
			throw new OrderException(
					"A data de criação do usuário informada para cálculo de desconto não pode ser nula");
		}

		var yearCreateUser = createDate.getYear();
		int currentYear = LocalDateTime.now(ZoneOffset.UTC).getYear();

		var percent = (currentYear - yearCreateUser) * 2;

		if (percent > 0 && percent < 96) {
			order.applyDiscount(percent);
		}

	}

}
