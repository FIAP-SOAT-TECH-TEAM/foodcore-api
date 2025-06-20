package com.soat.fiap.food.core.api.user.core.application.usecases;

import com.soat.fiap.food.core.api.shared.core.domain.exceptions.ResourceNotFoundException;
import com.soat.fiap.food.core.api.user.core.domain.model.User;

import java.util.Optional;

public class DeleteUserUseCase {
    public void deleteUser(Long id) {
        logger.debug("Excluindo usuário com ID: {}", id);

        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            logger.warn("Tentativa de excluir usuário inexistente. ID: {}", id);
            throw new ResourceNotFoundException("Usuário", "id", id);
        }

        userRepository.delete(id);
        logger.debug("Usuário excluído com sucesso. ID: {}", id);
    }
}
