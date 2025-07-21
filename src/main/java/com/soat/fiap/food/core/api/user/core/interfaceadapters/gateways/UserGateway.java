package com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.soat.fiap.food.core.api.user.core.domain.model.User;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.dto.UserDTO;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.dto.mappers.UserDTOMapper;
import com.soat.fiap.food.core.api.user.infrastructure.common.source.UserDataSource;

/**
 * Gateway para persistência do agregado Usuário.
 */
public class UserGateway {

	private final UserDataSource userDataSource;

	public UserGateway(UserDataSource userDataSource) {
		this.userDataSource = userDataSource;
	}

	/**
	 * Salva o agregado Usuário.
	 *
	 * @param user
	 *            Agregado Usuário a ser salvo
	 * @return Usuário salvo com identificador atualizado
	 */
	public User save(User user) {
		UserDTO dto = UserDTOMapper.toDTO(user);
		UserDTO savedDTO = userDataSource.save(dto);
		return UserDTOMapper.toDomain(savedDTO);
	}

	/**
	 * Busca um usuário pelo ID.
	 *
	 * @param id
	 *            ID do usuário
	 * @return Optional contendo o usuário ou vazio se não encontrado
	 */
	public Optional<User> findById(Long id) {
		return userDataSource.findById(id).map(UserDTOMapper::toDomain);
	}

	/**
	 * Busca um usuário pelo documento.
	 *
	 * @param document
	 *            Documento do usuário
	 * @return Optional contendo o usuário ou vazio se não encontrado
	 */
	public Optional<User> findByDocument(String document) {
		return userDataSource.findByDocument(document).map(UserDTOMapper::toDomain);
	}

	/**
	 * Busca um usuário pelo e-mail.
	 *
	 * @param email
	 *            E-mail do usuário
	 * @return Optional contendo o usuário ou vazio se não encontrado
	 */
	public Optional<User> findByEmail(String email) {
		return userDataSource.findByEmail(email).map(UserDTOMapper::toDomain);
	}

	/**
	 * Busca um usuário pelo nome de usuário.
	 *
	 * @param username
	 *            Nome de usuário
	 * @return Optional contendo o usuário ou vazio se não encontrado
	 */
	public Optional<User> findByUsername(String username) {
		return userDataSource.findByUsername(username).map(UserDTOMapper::toDomain);
	}

	/**
	 * Busca um usuário pela role.
	 *
	 * @param roleId
	 *            ID da role do usuário
	 * @return Optional contendo o usuário ou vazio se não encontrado
	 */
	public Optional<User> findByRoleId(Long roleId) {
		return userDataSource.findByRoleId(roleId).map(UserDTOMapper::toDomain);
	}

	/**
	 * Busca o primeiro usuário convidado (guest).
	 *
	 * @return Optional contendo o usuário convidado ou vazio se não encontrado
	 */
	public Optional<User> findFirstByGuestTrue() {
		return userDataSource.findFirstByGuestTrue().map(UserDTOMapper::toDomain);
	}

	/**
	 * Lista todos os usuários.
	 *
	 * @return Lista de usuários
	 */
	public List<User> findAll() {
		return userDataSource.findAll().stream().map(UserDTOMapper::toDomain).collect(Collectors.toList());
	}

	/**
	 * Lista apenas os usuários ativos.
	 *
	 * @return Lista de usuários ativos
	 */
	public List<User> findAllActive() {
		return userDataSource.findAllActive().stream().map(UserDTOMapper::toDomain).collect(Collectors.toList());
	}

	/**
	 * Remove um usuário com base em seu ID.
	 *
	 * @param id
	 *            ID do usuário a ser removido
	 */
	public void delete(Long id) {
		userDataSource.delete(id);
	}
}
