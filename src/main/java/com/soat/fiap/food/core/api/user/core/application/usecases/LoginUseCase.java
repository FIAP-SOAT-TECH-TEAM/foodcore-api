package com.soat.fiap.food.core.api.user.core.application.usecases;

import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.SecurityGateway;
import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserIncorrectPasswordException;
import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserNotFoundException;
import com.soat.fiap.food.core.api.user.core.domain.model.User;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Login de usuário.
 */
@Slf4j
public class LoginUseCase {

	/**
	 * Realiza login do usuário.
	 *
	 * @param email
	 *            Email do usuário
	 * @param rawPassword
	 *            Senha em texto puro
	 * @param userGateway
	 *            Gateway para consulta de usuários
	 * @param securityGateway
	 *            Gateway para verificação de senha
	 * @return Usuário autenticado
	 * @throws UserNotFoundException
	 *             Caso o usuário não seja encontrado
	 * @throws UserIncorrectPasswordException
	 *             Caso a senha seja inválida
	 */
	public static User login(String email, String rawPassword, UserGateway userGateway,
			SecurityGateway securityGateway) {
		var optionalUser = userGateway.findByEmail(email);

		if (optionalUser.isEmpty()) {
			log.warn("Usuário não encontrado para o email: {}", email);
			throw new UserNotFoundException("Usuário não encontrado!");
		}

		var user = optionalUser.get();
		user.validateInternalState();
		if (!securityGateway.matches(rawPassword, user.getPassword())) {
			log.warn("Senha incorreta para o email: {}", email);
			throw new UserIncorrectPasswordException("Email ou senha inválidos");
		}

		log.debug("Usuário autenticado com sucesso. ID: {}", user.getId());
		return user;
	}
}
