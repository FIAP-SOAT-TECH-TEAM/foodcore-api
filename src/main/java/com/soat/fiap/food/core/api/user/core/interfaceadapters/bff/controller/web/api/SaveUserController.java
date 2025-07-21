package com.soat.fiap.food.core.api.user.core.interfaceadapters.bff.controller.web.api;

import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.SecurityGateway;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.TokenGateway;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.SecuritySource;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.TokenSource;
import com.soat.fiap.food.core.api.user.core.application.inputs.mappers.UserMapper;
import com.soat.fiap.food.core.api.user.core.application.usecases.CreateUserUseCase;
import com.soat.fiap.food.core.api.user.core.application.usecases.GenerateTokenUseCase;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.bff.presenter.web.api.UserPresenter;
import com.soat.fiap.food.core.api.user.infrastructure.common.source.UserDataSource;
import com.soat.fiap.food.core.api.user.infrastructure.in.web.api.dto.request.UserRequest;
import com.soat.fiap.food.core.api.user.infrastructure.in.web.api.dto.response.UserResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Salvar usuário.
 */
@Slf4j
public class SaveUserController {

	/**
	 * Salva um usuário.
	 *
	 * @param userRequest
	 *            Dados do usuário a ser salvo
	 * @param userDataSource
	 *            Origem de dados para o gateway
	 * @param tokenSource
	 *            Origem de dados para geração de token
	 * @param securitySource
	 *            Origem de dados para serviços de segurança
	 * @return Usuário salvo com identificadores atualizados
	 */
	public static UserResponse saveUser(UserRequest userRequest, UserDataSource userDataSource, TokenSource tokenSource,
			SecuritySource securitySource) {

		var userInput = UserMapper.toInput(userRequest);
		var userGateway = new UserGateway(userDataSource);
		var tokenGateway = new TokenGateway(tokenSource);
		var securityGateway = new SecurityGateway(securitySource);

		var user = CreateUserUseCase.createUser(userInput, userGateway, tokenGateway, securityGateway);

		var savedUser = userGateway.save(user);

		var userWithToken = GenerateTokenUseCase.generateToken(savedUser, tokenGateway);

		log.debug("Usuário criado com sucesso. ID: {}", savedUser.getId());

		return UserPresenter.toUserResponse(userWithToken);
	}
}
