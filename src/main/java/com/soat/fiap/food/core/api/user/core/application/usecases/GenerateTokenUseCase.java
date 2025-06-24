package com.soat.fiap.food.core.api.user.core.application.usecases;

import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.TokenGateway;
import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserException;
import com.soat.fiap.food.core.api.user.core.domain.model.User;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Gerar token para o usuário.
 */
@Slf4j
public class GenerateTokenUseCase {

	/**
	 * Gera um token para o usuário informado.
	 *
	 * @param user
	 *            Usuário para o qual o token será gerado.
	 * @param tokenGateway
	 *            Gateway responsável pela geração do token.
	 * @return Usuário com o token atribuído.
	 * @throws UserException
	 *             Caso o usuário seja nulo ou não possua ID.
	 */
	public static User generateToken(User user, TokenGateway tokenGateway) {
		if (user == null || user.getId() == null) {
			log.warn("Tentativa de gerar token para usuário nulo ou sem id");
			throw new UserException("Um usuário não nulo e com ID deve ser informado para geração do token");
		}

		var token = tokenGateway.generateToken(user);
		user.setToken(token);

		return user;
	}
}
