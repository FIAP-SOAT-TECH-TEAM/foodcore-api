package com.soat.fiap.food.core.api.user.core.application.usecases;

import com.soat.fiap.food.core.api.shared.core.domain.exceptions.ResourceConflictException;
import com.soat.fiap.food.core.api.shared.core.domain.exceptions.ResourceNotFoundException;
import com.soat.fiap.food.core.api.user.core.domain.model.User;

import java.util.Optional;

public class UpdateUserUseCase {

    public User updateUser(Long id, User user) {
        logger.debug("Atualizando usuário com ID: {}", id);

        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isEmpty()) {
            logger.warn("Usuário não encontrado com ID: {}", id);
            throw new ResourceNotFoundException("Usuário", "id", id);
        }

        if (!existingUser.get().getDocument().equals(user.getDocument())) {
            Optional<User> userWithSameDocument = userRepository.findByDocument(user.getDocument());
            if (userWithSameDocument.isPresent() && !userWithSameDocument.get().getId().equals(id)) {
                logger.warn("Tentativa de atualizar usuário para documento já existente: {}", user.getDocument());
                throw new ResourceConflictException("usuário", "documento", user.getDocument());
            }
        }

        user.setId(id);
        User updatedUser = userRepository.save(user);
        logger.debug("Usuário atualizado com sucesso. ID: {}", updatedUser.getId());

        return updatedUser;
    }
}
