package com.soat.fiap.food.core.api.user.core.interfaceadapters.controller.web.api;

import com.soat.fiap.food.core.api.user.core.application.usecases.GetUserByIdUseCase;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.presenter.web.api.UserPresenter;
import com.soat.fiap.food.core.api.user.infrastructure.common.source.UserDataSource;
import com.soat.fiap.food.core.api.user.infrastructure.in.web.api.dto.response.UserResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Buscar um usuário pelo seu ID.
 *
 */
@Slf4j
public class GetUserByIdController {

    /**
     * Busca um usuário pelo seu ID.
     *
     * @param id Identificador do usuário
     * @param userDataSource Origem de dados para o gateway
     * @return o usuário
     */
    public static UserResponse getUserById(Long id, UserDataSource userDataSource) {
        log.debug("Buscando usuário de id: {}", id);

        var gateway = new UserGateway(userDataSource);

        var existingUser = GetUserByIdUseCase.getUserById(id, gateway);

        return UserPresenter.toUserResponse(existingUser);
    }
}
