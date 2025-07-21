package com.soat.fiap.food.core.api.user.core.application.usecases;

import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserNotFoundException;
import com.soat.fiap.food.core.api.user.core.domain.model.User;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Obter usuário por documento.
 */
@Slf4j
public class GetUserByDocumentUseCase {

	/**
	 * Busca um usuário pelo seu documento.
	 *
	 * @param document
	 *            documento do usuário
	 * @param userGateway
	 *            Gateway para comunicação com o mundo exterior
	 * @return o usuário
	 */
	public static User getUserByDocument(String document, UserGateway userGateway) {
		log.debug("Buscando usuários com documento: {}", document);

		var user = userGateway.findByDocument(document);

		if (user.isEmpty()) {
			log.debug("Usuário não encontrado com documento: {}", document);
			throw new UserNotFoundException("Usuário", "Documento", document);
		}

		return user.get();
	}

}
