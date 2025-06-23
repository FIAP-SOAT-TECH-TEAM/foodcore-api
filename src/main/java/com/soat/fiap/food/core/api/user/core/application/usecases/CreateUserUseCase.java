package com.soat.fiap.food.core.api.user.core.application.usecases;

import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.SecurityGateway;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.TokenGateway;
import com.soat.fiap.food.core.api.user.core.application.inputs.UserInput;
import com.soat.fiap.food.core.api.user.core.application.inputs.mappers.UserMapper;
import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserNotFoundException;
import com.soat.fiap.food.core.api.user.core.domain.model.User;
import com.soat.fiap.food.core.api.user.core.domain.vo.RoleType;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Criar usuário.
 */
@Slf4j
public class CreateUserUseCase {

	/**
	 * Cria um usuário.
	 *
	 * @param userInput
	 *            Dados do usuário a ser criado
	 * @param userGateway
	 *            Gateway para comunicação com o mundo exterior
	 * @param tokenGateway
	 *            Gateway para geração de token JWT
	 * @param securityGateway
	 *            Gateway para serviços de segurança
	 * @return Usuário criado
	 * @throws UserNotFoundException
	 *             Caso usuário seja GUEST e nenhum seja encontrado no banco
	 */
	public static User createUser(UserInput userInput, UserGateway userGateway, TokenGateway tokenGateway,
			SecurityGateway securityGateway) {
		var user = UserMapper.toDomain(userInput);

		if (user.isGuest()) {
			user = userGateway.findByRoleId((long) RoleType.GUEST.getId())
					.orElseThrow(() -> new UserNotFoundException("Usuário GUEST não encontrado no banco."));
		}
		if (user.hasDocument()) {
			var existingByDocument = userGateway.findByDocument(user.getDocument());

			if (existingByDocument.isPresent()) {
				user = existingByDocument.get();
			}
		}
		if (user.hasEmail()) {
			var existingByEmail = userGateway.findByEmail(user.getEmail());

			if (existingByEmail.isPresent()) {
				user = existingByEmail.get();
			}
		}

		if (user.hasPassword() && user.getId() == null) {
			var securePassword = securityGateway.encodePassword(user.getPassword());
			user.setPassword(securePassword);
		}

		return user;
	}
}
