package com.soat.fiap.food.core.api.user.core.application.usecases;

import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserNotFoundException;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Remover usuário pelo seu identificador.
 */
@Slf4j
public class DeleteUserUseCase {
	/**
	 * Remove um usuário pelo seu ID.
	 *
	 * @param id
	 *            Identificador do usuário a ser removido
	 * @param userGateway
	 *            Gateway para comunicação com o mundo exterior
	 */
	public static void deleteUser(Long id, UserGateway userGateway) {
		log.debug("Excluindo usuário com ID: {}", id);

		var user = userGateway.findById(id);
		if (user.isEmpty()) {
			log.warn("Tentativa de excluir usuário inexistente. ID: {}", id);
			throw new UserNotFoundException("Usuário", id);
		}

		userGateway.delete(id);
		log.debug("Usuário excluído com sucesso. ID: {}", id);
	}
}
