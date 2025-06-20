package com.soat.fiap.food.core.api.user.core.application.usecases;

import com.soat.fiap.food.core.api.user.core.domain.model.User;

import java.util.Optional;

public class GetUserByDocumentUseCase {
    public Optional<User> getUserByDocument(String document) {
        logger.debug("Buscando usuários com documento: {}", document);
        return userRepository.findByDocument(document);
    }
}
