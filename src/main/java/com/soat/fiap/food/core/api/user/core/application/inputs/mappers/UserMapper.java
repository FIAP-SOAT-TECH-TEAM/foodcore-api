package com.soat.fiap.food.core.api.user.core.application.inputs.mappers;

import com.soat.fiap.food.core.api.user.core.application.inputs.UserInput;
import com.soat.fiap.food.core.api.user.core.domain.model.User;
import com.soat.fiap.food.core.api.user.infrastructure.in.web.api.dto.request.UserRequest;

/**
 * Classe utilitária responsável por mapear objetos entre {@link UserRequest} e
 * {@link UserInput}.
 */
public class UserMapper {

	/**
	 * Converte um {@link UserRequest} para um {@link UserInput}.
	 *
	 * @param request
	 *            Objeto de requisição da camada web.
	 * @return Objeto {@link UserInput} com os dados extraídos do request.
	 */
	public static UserInput toInput(UserRequest request) {
		return new UserInput(request.isGuest(), request.getName(), request.getUsername(), request.getEmail(),
				request.getPassword(), request.getDocument());
	}

	/**
	 * Converte um {@link UserInput} (camada de aplicação) em um {@link User}
	 * (entidade de domínio).
	 *
	 * @param input
	 *            O DTO de entrada da aplicação.
	 * @return Uma entidade {@link User} representando o modelo de domínio.
	 */
	public static User toDomain(UserInput input) {
		return new User(input.guest(), input.name(), input.username(), input.email(), input.password(),
				input.document());
	}
}
