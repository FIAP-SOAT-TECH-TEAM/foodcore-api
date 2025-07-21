package com.soat.fiap.food.core.api.user.core.interfaceadapters.bff.controller.web.api;

import java.util.List;

import com.soat.fiap.food.core.api.user.core.application.usecases.GetAllUsersUseCase;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.bff.presenter.web.api.UserPresenter;
import com.soat.fiap.food.core.api.user.infrastructure.common.source.UserDataSource;
import com.soat.fiap.food.core.api.user.infrastructure.in.web.api.dto.response.UserResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Obter todos os usuários.
 */
@Slf4j
public class GetAllUsersController {

	/**
	 * Obtém todos os usuários.
	 *
	 * @param userDataSource
	 *            Origem de dados para o gateway
	 * @return os usuários
	 */
	public static List<UserResponse> getAllUsers(UserDataSource userDataSource) {
		log.debug("Buscando todos os usuários");

		var gateway = new UserGateway(userDataSource);

		var existingUsers = GetAllUsersUseCase.getAllUsers(gateway);

		return UserPresenter.toListUserResponse(existingUsers);
	}
}
