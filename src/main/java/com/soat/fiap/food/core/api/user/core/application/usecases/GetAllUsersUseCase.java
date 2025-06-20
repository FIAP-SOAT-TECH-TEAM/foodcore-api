package com.soat.fiap.food.core.api.user.core.application.usecases;

import com.soat.fiap.food.core.api.user.core.domain.model.User;

import java.util.List;

public class GetAllUsersUseCase {
    public List<User> getAllUsers() {
        logger.debug("Buscando todos os usuários");
        List<User> users = userRepository.findAll();
        logger.debug("Total de usuários encontrados: {}", users.size());
        return users;
    }
}
