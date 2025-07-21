package com.soat.fiap.food.core.api.order.core.application.usecases;

import com.soat.fiap.food.core.api.order.core.domain.model.Order;
import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserNotFoundException;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Atribuir usuário do tipo GUEST ao pedido.
 */
@Slf4j
public class AssignGuestUserToOrderUseCase {

	/**
	 * Valida se o pedido possui um usuário associado.
	 * <p>
	 * Caso não tenha, tenta associar automaticamente o usuário do tipo
	 * {@code GUEST}. Se não for possível encontrar esse usuário, lança uma exceção.
	 *
	 * @param order
	 *            o pedido a ser validado e atualizado
	 * @param gateway
	 *            Gateway para comunicação com o mundo exterior
	 * @throws UserNotFoundException
	 *             se o usuário do tipo {@code GUEST} não for encontrado
	 */
	public static void assignGuestUserToOrder(Order order, UserGateway gateway) {
		if (order.getUserId() == null) {
			var userGuest = gateway.findFirstByGuestTrue();

			if (userGuest.isEmpty()) {
				throw new UserNotFoundException("Usuário GUEST não encontrado");
			}

			order.setUserId(userGuest.get().getId());
		}
	}
}
