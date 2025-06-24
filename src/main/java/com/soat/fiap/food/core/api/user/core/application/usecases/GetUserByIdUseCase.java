package com.soat.fiap.food.core.api.user.core.application.usecases;

import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserNotFoundException;
import com.soat.fiap.food.core.api.user.core.domain.model.User;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Obter usuário por identificador.
 */
@Slf4j
public class GetUserByIdUseCase {

	/**
	 * Busca um usuário pelo seu ID.
	 *
	 * @param id
	 *            Identificador do usuário
	 * @param userGateway
	 *            Gateway para comunicação com o mundo exterior
	 * @return o usuário
	 */
	public static User getUserById(Long id, UserGateway userGateway) {
		log.debug("Buscando usuário com ID: {}", id);
		var user = userGateway.findById(id);

		if (user.isEmpty()) {
			log.debug("Usuário não encontrado com ID: {}", id);
			throw new UserNotFoundException("Usuário", id);
		}

		return user.get();
	}
}
