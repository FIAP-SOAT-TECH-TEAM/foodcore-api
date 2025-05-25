package com.soat.fiap.food.core.api.user.application.services;

import com.soat.fiap.food.core.api.shared.exception.BusinessException;
import com.soat.fiap.food.core.api.shared.exception.ResourceConflictException;
import com.soat.fiap.food.core.api.shared.exception.ResourceNotFoundException;
import com.soat.fiap.food.core.api.shared.infrastructure.logging.CustomLogger;
import com.soat.fiap.food.core.api.user.application.ports.in.UserUseCase;
import com.soat.fiap.food.core.api.user.application.ports.out.UserRepository;
import com.soat.fiap.food.core.api.user.domain.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementação do caso de uso de Usuário.
 */
@Service
public class UserService implements UserUseCase {

    private final UserRepository userRepository;
    private final CustomLogger logger;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.logger = CustomLogger.getLogger(getClass());
    }

    @Override
    @Transactional
    public User createUser(User user) {

        if(!user.getDocument().isEmpty()) {
            if (!user.isValidDocument()) {
                logger.warn("Tentativa de criar usuário com documento inválido: {}", user.getDocument());
                throw new BusinessException("Documento inválido");
            }

            Optional<User> existingDocument = userRepository.findByDocument(user.getDocument());
            if (existingDocument.isPresent()) {
                logger.warn("Tentativa de criar usuário com documento já existente: {}", user.getDocument());
                throw new ResourceConflictException("usuário", "documento", user.getDocument());
            }

        }

        if(!user.getEmail().isEmpty()){
            Optional<User> existingEmail = userRepository.findByEmail(user.getEmail());
            if (existingEmail.isPresent()) {
                logger.warn("Tentativa de criar usuário com email já existente: {}", user.getEmail());
                throw new ResourceConflictException("usuário", "email", user.getEmail());
            }
        }

        if(!user.getUsername().isEmpty()){
            Optional<User> existingUsername = userRepository.findByUsername(user.getUsername());
            if (existingUsername.isPresent()) {
                logger.warn("Tentativa de criar usuário com username já existente: {}", user.getUsername());
                throw new ResourceConflictException("usuário", "username", user.getUsername());
            }
        }

        user.activate();
        User savedUser = userRepository.save(user);
        logger.debug("Usuário criado com sucesso. ID: {}", savedUser.getId());
        
        return savedUser;
    }

    @Override
    @Transactional
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

    @Override
    @Transactional(readOnly = true)
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

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByDocument(String document) {
        logger.debug("Buscando usuários com documento: {}", document);
        return userRepository.findByDocument(document);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        logger.debug("Buscando todos os usuários");
        List<User> users = userRepository.findAll();
        logger.debug("Total de usuários encontrados: {}", users.size());
        return users;
    }

    @Override
    @Transactional
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