package com.soat.fiap.food.core.api.user.core.interfaceadapters.controller.web.api;

import com.soat.fiap.food.core.api.user.core.application.usecases.DeleteUserUseCase;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;
import com.soat.fiap.food.core.api.user.infrastructure.common.source.UserDataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Excluir usuário.
 *
 */
@Slf4j
public class DeleteUserController {

    /**
     * Remove um usuário pelo seu ID.
     *
     * @param id Identificador do usuário a ser removido
     * @param userDataSource Origem de dados para o gateway
     */
    public static void deleteUser(Long id, UserDataSource userDataSource) {
        log.debug("Excluindo usuário de id: {}", id);

        var gateway = new UserGateway(userDataSource);

        DeleteUserUseCase.deleteUser(id, gateway);
    }
}
