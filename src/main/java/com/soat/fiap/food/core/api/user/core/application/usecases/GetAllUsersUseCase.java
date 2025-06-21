package com.soat.fiap.food.core.api.user.core.application.usecases;

import com.soat.fiap.food.core.api.user.core.domain.model.User;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Caso de uso: Obter todos usuários.
 *
 */
@Slf4j
public class GetAllUsersUseCase {

    /**
     * Obtém todos os usuários.
     *
     * @return Lista contendo todos os usuários
     */
    public static List<User> getAllUsers(UserGateway gateway) {
        log.debug("Buscando todos os usuários");
        var users = gateway.findAll();
        log.debug("Total de usuários encontrados: {}", users.size());
        return users;
    }
}
