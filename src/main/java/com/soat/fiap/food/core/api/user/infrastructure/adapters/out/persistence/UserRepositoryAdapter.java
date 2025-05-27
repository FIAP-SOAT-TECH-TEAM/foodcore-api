package com.soat.fiap.food.core.api.user.infrastructure.adapters.out.persistence;

import com.soat.fiap.food.core.api.user.application.ports.out.UserRepository;
import com.soat.fiap.food.core.api.user.domain.model.User;
import com.soat.fiap.food.core.api.user.infrastructure.adapters.out.persistence.mapper.UserEntityMapper;
import com.soat.fiap.food.core.api.user.infrastructure.adapters.out.persistence.repository.SpringDataUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador que implementa a porta de saída UserRepository
 */
@Component
public class UserRepositoryAdapter implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;
    private final UserEntityMapper userEntityMapper;

    public UserRepositoryAdapter(
            SpringDataUserRepository springDataUserRepository,
            UserEntityMapper userEntityMapper) {
        this.springDataUserRepository = springDataUserRepository;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public User save(User user) {
        var userEntity = userEntityMapper.toEntity(user);
        var savedEntity = springDataUserRepository.save(userEntity);
        return userEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(Long id) {
        return springDataUserRepository.findById(id)
                .map(userEntityMapper::toDomain);
    }

    @Override
    public Optional<User> findByDocument(String document) {
        return springDataUserRepository.findByDocument(document)
                .map(userEntityMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return springDataUserRepository.findByEmail(email)
                .map(userEntityMapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return springDataUserRepository.findByUsername(username)
                .map(userEntityMapper::toDomain);
    }

    public Optional<User> findByRoleId(Long roleId) {
        return springDataUserRepository.findByRoleId(roleId)
                .map(userEntityMapper::toDomain); // converte de entidade para domínio
    }


    @Override
    public List<User> findAll() {
        var userEntities = springDataUserRepository.findAll();
        return userEntityMapper.toDomainList(userEntities);
    }

    @Override
    public List<User> findAllActive() {
        var userEntities = springDataUserRepository.findByActiveTrue();
        return userEntityMapper.toDomainList(userEntities);
    }

    @Override
    public void delete(Long id) {
        springDataUserRepository.deleteById(id);
    }
} 