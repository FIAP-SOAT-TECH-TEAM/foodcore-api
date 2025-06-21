package com.soat.fiap.food.core.api.user.core.application.usecases;

import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.SecurityGateway;
import com.soat.fiap.food.core.api.user.core.application.inputs.UserInput;
import com.soat.fiap.food.core.api.user.core.application.inputs.mappers.UserMapper;
import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserConflictException;
import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserNotFoundException;
import com.soat.fiap.food.core.api.user.core.domain.model.User;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Atualizar usuário.
 *
 */
@Slf4j
public class UpdateUserUseCase {


    /**
     * Atualiza um usuário.
     *
     * @param id Identificador do usuário a ser atualizado
     * @param userInput Dados atualizados do usuário
     * @param userGateway Gateway para acesso ao repositório de usuários
     * @param securityGateway Gateway para operações de segurança, como criptografia de senha
     * @return Usuário atualizado com senha segura
     * @throws UserNotFoundException Se o usuário não for encontrado
     * @throws UserConflictException Se o documento já estiver sendo utilizado por outro usuário
     */
    public static User updateUser(Long id, UserInput userInput, UserGateway userGateway, SecurityGateway securityGateway) {
        log.debug("Atualizando usuário com ID: {}", id);

        var user = UserMapper.toDomain(userInput);
        var existingUser = userGateway.findById(id);

        if (existingUser.isEmpty()) {
            log.warn("Usuário não encontrado com ID: {}", id);
            throw new UserNotFoundException("Usuário", id);
        }

        if (!existingUser.get().getDocument().equals(userInput.document())) {
            var userWithSameDocument = userGateway.findByDocument(userInput.document());

            if (userWithSameDocument.isPresent() && !userWithSameDocument.get().getId().equals(id)) {
                log.warn("Tentativa de atualizar usuário para documento já existente: {}", userInput.document());
                throw new UserConflictException("usuário", "documento", userInput.document());
            }
        }

        user.setId(id);
        var securePassword = securityGateway.encodePassword(user.getPassword());
        user.setPassword(securePassword);

        return user;
    }
}
