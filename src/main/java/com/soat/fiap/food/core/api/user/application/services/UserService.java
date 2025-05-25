package com.soat.fiap.food.core.api.user.application.services;

import com.soat.fiap.food.core.api.shared.exception.BusinessException;
import com.soat.fiap.food.core.api.shared.exception.ResourceConflictException;
import com.soat.fiap.food.core.api.shared.exception.ResourceNotFoundException;
import com.soat.fiap.food.core.api.shared.infrastructure.logging.CustomLogger;
import com.soat.fiap.food.core.api.shared.vo.RoleType;
import com.soat.fiap.food.core.api.user.application.ports.in.UserUseCase;
import com.soat.fiap.food.core.api.user.application.ports.out.UserRepository;
import com.soat.fiap.food.core.api.user.domain.model.Role;
import com.soat.fiap.food.core.api.user.domain.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementação do caso de uso de Usuário.
 */
@Service
public class UserService implements UserUseCase {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CustomLogger logger;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.logger = CustomLogger.getLogger(getClass());
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public User createUser(User user) {

        if (user.isGuest()) {
            user.markAsGuest();

            if (user.getPassword() != null && !user.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }

        } else {
            if (user.getDocument() != null && !user.getDocument().isBlank()) {
                if (!user.isValidDocument()) {
                    logger.warn("Tentativa de criar usuário com documento inválido: {}", user.getDocument());
                    throw new BusinessException("Documento inválido");
                }

                Optional<User> existingDocument = userRepository.findByDocument(user.getDocument());
                if (existingDocument.isPresent()) {
                    throw new ResourceConflictException("usuário", "documento", user.getDocument());
                }
            }

            if (user.getEmail() != null && !user.getEmail().isBlank()) {
                Optional<User> existingEmail = userRepository.findByEmail(user.getEmail());
                if (existingEmail.isPresent()) {
                    throw new ResourceConflictException("usuário", "email", user.getEmail());
                }
            }

            if (user.getUsername() != null && !user.getUsername().isBlank()) {
                Optional<User> existingUsername = userRepository.findByUsername(user.getUsername());
                if (existingUsername.isPresent()) {
                    throw new ResourceConflictException("usuário", "username", user.getUsername());
                }
            }

            user.activate();

            // Define role USER caso não seja especificado
            if (user.getRole() == null || user.getRole().getId() == 0) {
                Role defaultRole = new Role();
                defaultRole.setId((long) RoleType.USER.getId());
                defaultRole.setName(RoleType.USER.name());
                user.setRole(defaultRole);
            }
            if (user.getPassword() != null && !user.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }

        }

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