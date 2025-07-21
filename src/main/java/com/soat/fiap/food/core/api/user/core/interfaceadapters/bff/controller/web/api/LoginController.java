package com.soat.fiap.food.core.api.user.core.interfaceadapters.bff.controller.web.api;

import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.SecurityGateway;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.TokenGateway;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.SecuritySource;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.TokenSource;
import com.soat.fiap.food.core.api.user.core.application.usecases.GenerateTokenUseCase;
import com.soat.fiap.food.core.api.user.core.application.usecases.LoginUseCase;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.bff.presenter.web.api.UserPresenter;
import com.soat.fiap.food.core.api.user.infrastructure.common.source.UserDataSource;
import com.soat.fiap.food.core.api.user.infrastructure.in.web.api.dto.response.UserResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Login de usuário.
 */
@Slf4j
public class LoginController {

	/**
	 * Realiza login do usuário.
	 *
	 * @param email
	 *            Email do usuário
	 * @param rawPassword
	 *            Senha em texto puro
	 * @param userDataSource
	 *            Origem de dados do usuário
	 * @param tokenSource
	 *            Origem de dados para geração de token
	 * @param securitySource
	 *            Origem de dados para serviços de segurança
	 * @return Usuário autenticado com token
	 */
	public static UserResponse login(String email, String rawPassword, UserDataSource userDataSource,
			TokenSource tokenSource, SecuritySource securitySource) {

		var userGateway = new UserGateway(userDataSource);
		var tokenGateway = new TokenGateway(tokenSource);
		var securityGateway = new SecurityGateway(securitySource);

		var user = LoginUseCase.login(email, rawPassword, userGateway, securityGateway);
		var userWithToken = GenerateTokenUseCase.generateToken(user, tokenGateway);

		return UserPresenter.toUserResponse(userWithToken);
	}
}
