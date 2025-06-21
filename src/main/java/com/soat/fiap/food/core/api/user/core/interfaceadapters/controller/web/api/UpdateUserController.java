package com.soat.fiap.food.core.api.user.core.interfaceadapters.controller.web.api;

import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.SecurityGateway;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.SecuritySource;
import com.soat.fiap.food.core.api.user.core.application.inputs.mappers.UserMapper;
import com.soat.fiap.food.core.api.user.core.application.usecases.UpdateUserUseCase;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.presenter.web.api.UserPresenter;
import com.soat.fiap.food.core.api.user.infrastructure.common.source.UserDataSource;
import com.soat.fiap.food.core.api.user.infrastructure.in.web.api.dto.request.UserRequest;
import com.soat.fiap.food.core.api.user.infrastructure.in.web.api.dto.response.UserResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Atualizar usuário.
 *
 */
@Slf4j
public class UpdateUserController {

    /**
     * Atualiza um usuário.
     *
     * @param id Identificador do usuário a ser atualizado
     * @param userRequest Requisição com dados do usuário
     * @param userDataSource Fonte de dados utilizada pelo gateway
     * @param securitySource Fonte de dados para operações de segurança
     * @return Resposta com os dados do usuário atualizado
     */
    public static UserResponse updateUser(Long id, UserRequest userRequest, UserDataSource userDataSource, SecuritySource securitySource) {

        var userInput = UserMapper.toInput(userRequest);
        var userGateway = new UserGateway(userDataSource);
        var securityGateway = new SecurityGateway(securitySource);

        var user = UpdateUserUseCase.updateUser(id, userInput, userGateway, securityGateway);
        var updatedUser = userGateway.save(user);

        log.debug("Usuário atualizado com sucesso. ID: {}", updatedUser.getId());

        return UserPresenter.toUserResponse(updatedUser);
    }
}
