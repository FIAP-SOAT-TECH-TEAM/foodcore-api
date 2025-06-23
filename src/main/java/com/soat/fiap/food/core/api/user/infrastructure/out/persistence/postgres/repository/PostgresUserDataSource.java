package com.soat.fiap.food.core.api.user.infrastructure.out.persistence.postgres.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.soat.fiap.food.core.api.user.core.domain.model.User;
import com.soat.fiap.food.core.api.user.infrastructure.common.source.UserDataSource;
import com.soat.fiap.food.core.api.user.infrastructure.out.persistence.postgres.mapper.UserEntityMapper;

/**
 * Implementação concreta: DataSource para persistência do agregado Usuário.
 */
@Component
public class PostgresUserDataSource implements UserDataSource {

	private final SpringDataUserRepository springDataUserRepository;
	private final UserEntityMapper userEntityMapper;

	public PostgresUserDataSource(SpringDataUserRepository springDataUserRepository,
			UserEntityMapper userEntityMapper) {
		this.springDataUserRepository = springDataUserRepository;
		this.userEntityMapper = userEntityMapper;
	}

	@Transactional
	public User save(User user) {
		var userEntity = userEntityMapper.toEntity(user);
		var savedEntity = springDataUserRepository.save(userEntity);
		return userEntityMapper.toDomain(savedEntity);
	}

	@Transactional(readOnly = true)
	public Optional<User> findById(Long id) {
		return springDataUserRepository.findById(id).map(userEntityMapper::toDomain);
	}

	@Transactional(readOnly = true)
	public Optional<User> findByDocument(String document) {
		return springDataUserRepository.findByDocument(document).map(userEntityMapper::toDomain);
	}

	@Transactional(readOnly = true)
	public Optional<User> findByEmail(String email) {
		return springDataUserRepository.findByEmail(email).map(userEntityMapper::toDomain);
	}

	@Transactional(readOnly = true)
	public Optional<User> findByUsername(String username) {
		return springDataUserRepository.findByUsername(username).map(userEntityMapper::toDomain);
	}

	@Transactional(readOnly = true)
	public Optional<User> findByRoleId(Long roleId) {
		return springDataUserRepository.findByRoleId(roleId).map(userEntityMapper::toDomain); // converte de entidade
																								// para domínio
	}

	@Transactional(readOnly = true)
	public Optional<User> findFirstByGuestTrue() {
		return springDataUserRepository.findFirstByGuestTrue().map(userEntityMapper::toDomain);
	}

	@Transactional(readOnly = true)
	public List<User> findAll() {
		var userEntities = springDataUserRepository.findAll();
		return userEntityMapper.toDomainList(userEntities);
	}

	@Transactional(readOnly = true)
	public List<User> findAllActive() {
		var userEntities = springDataUserRepository.findByActiveTrue();
		return userEntityMapper.toDomainList(userEntities);
	}

	@Transactional
	public void delete(Long id) {
		springDataUserRepository.deleteById(id);
	}
}
