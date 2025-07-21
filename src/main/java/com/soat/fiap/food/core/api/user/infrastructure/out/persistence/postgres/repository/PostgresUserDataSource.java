package com.soat.fiap.food.core.api.user.infrastructure.out.persistence.postgres.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.soat.fiap.food.core.api.user.core.interfaceadapters.dto.UserDTO;
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
	public UserDTO save(UserDTO userDTO) {
		var entity = userEntityMapper.toEntity(userDTO);
		var savedEntity = springDataUserRepository.save(entity);
		return userEntityMapper.toDTO(savedEntity);
	}

	@Transactional(readOnly = true)
	public Optional<UserDTO> findById(Long id) {
		return springDataUserRepository.findById(id).map(userEntityMapper::toDTO);
	}

	@Transactional(readOnly = true)
	public Optional<UserDTO> findByDocument(String document) {
		return springDataUserRepository.findByDocument(document).map(userEntityMapper::toDTO);
	}

	@Transactional(readOnly = true)
	public Optional<UserDTO> findByEmail(String email) {
		return springDataUserRepository.findByEmail(email).map(userEntityMapper::toDTO);
	}

	@Transactional(readOnly = true)
	public Optional<UserDTO> findByUsername(String username) {
		return springDataUserRepository.findByUsername(username).map(userEntityMapper::toDTO);
	}

	@Transactional(readOnly = true)
	public Optional<UserDTO> findByRoleId(Long roleId) {
		return springDataUserRepository.findByRoleId(roleId).map(userEntityMapper::toDTO);
	}

	@Transactional(readOnly = true)
	public Optional<UserDTO> findFirstByGuestTrue() {
		return springDataUserRepository.findFirstByGuestTrue().map(userEntityMapper::toDTO);
	}

	@Transactional(readOnly = true)
	public List<UserDTO> findAll() {
		return springDataUserRepository.findAll().stream().map(userEntityMapper::toDTO).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<UserDTO> findAllActive() {
		return springDataUserRepository.findByActiveTrue()
				.stream()
				.map(userEntityMapper::toDTO)
				.collect(Collectors.toList());
	}

	@Transactional
	public void delete(Long id) {
		springDataUserRepository.deleteById(id);
	}
}
