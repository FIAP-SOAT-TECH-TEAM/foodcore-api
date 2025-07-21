package com.soat.fiap.food.core.api.user.core.interfaceadapters.bff.controller.web.api;

import com.soat.fiap.food.core.api.user.core.application.usecases.GetUserByDocumentUseCase;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.bff.presenter.web.api.UserPresenter;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;
import com.soat.fiap.food.core.api.user.infrastructure.common.source.UserDataSource;
import com.soat.fiap.food.core.api.user.infrastructure.in.web.api.dto.response.UserResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Buscar um usuário pelo seu documento.
 */
@Slf4j
public class GetUserByDocumentController {

	/**
	 * Busca um usuário pelo seu documento.
	 *
	 * @param document
	 *            Documento do usuário
	 * @param userDataSource
	 *            Origem de dados para o gateway
	 * @return o usuário
	 */
	public static UserResponse getUserByDocument(String document, UserDataSource userDataSource) {
		log.debug("Buscando usuário de documento: {}", document);

		var gateway = new UserGateway(userDataSource);

		var existingUser = GetUserByDocumentUseCase.getUserByDocument(document, gateway);

		return UserPresenter.toUserResponse(existingUser);
	}
}
