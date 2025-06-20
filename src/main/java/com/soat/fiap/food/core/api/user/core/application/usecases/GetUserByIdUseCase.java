package com.soat.fiap.food.core.api.user.core.application.usecases;

import com.soat.fiap.food.core.api.user.core.domain.model.User;

import java.util.Optional;

public class GetUserByIdUseCase {
    public Optional<User> getUserById(Long id) {
        logger.debug("Buscando usuário com ID: {}", id);
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            logger.debug("Usuário não encontrado com ID: {}", id);
        } else {
            logger.debug("Usuário encontrado. Nome: {}", user.get().getName());
        }

        return user;
    }
}
