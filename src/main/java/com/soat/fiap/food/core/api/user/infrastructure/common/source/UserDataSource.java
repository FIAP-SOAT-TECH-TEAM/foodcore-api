package com.soat.fiap.food.core.api.user.infrastructure.common.source;

import java.util.List;
import java.util.Optional;

import com.soat.fiap.food.core.api.user.core.domain.model.User;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.dto.UserDTO;

/**
 * DataSource para persistência de usuários
 */
public interface UserDataSource {

	/**
	 * Salva um usuário
	 *
	 * @param userDTO
	 *            Dados do usuário a ser salvo
	 * @return Usuário salvo com ID gerado
	 */
	UserDTO save(UserDTO userDTO);

	/**
	 * Busca um usuário por ID
	 *
	 * @param id
	 *            ID do usuário
	 * @return Optional contendo o usuário ou vazio se não encontrado
	 */
	Optional<User> findById(Long id);

	/**
	 * Busca um usuário por DOCUMENT
	 *
	 * @param document
	 *            DOCUMENT do usuário
	 * @return Optional contendo o usuário ou vazio se não encontrado
	 */
	Optional<User> findByDocument(String document);

	/**
	 * Busca um usuário pelo seu email
	 *
	 * @param email
	 *            EMAIL do usuário
	 * @return Optional contendo o usuário ou vazio se não encontrado
	 */
	Optional<User> findByEmail(String email);

	/**
	 * Busca um usuário por seu username
	 *
	 * @param username
	 *            USERNAME do usuário
	 * @return Optional contendo o usuário ou vazio se não encontrado
	 */
	Optional<User> findByUsername(String username);

	/**
	 * Busca um usuário por sua role
	 *
	 * @param roleId
	 *            Role do usuário
	 * @return Optional contendo o usuário ou vazio se não encontrado
	 */

	Optional<User> findByRoleId(Long roleId);

	/**
	 * Busca o primeiro usuário com o campo guest igual a true.
	 *
	 * @return um {@link Optional} contendo o usuário encontrado ou vazio se não
	 *         houver nenhum.
	 */
	Optional<User> findFirstByGuestTrue();

	/**
	 * Lista todos os usuário
	 *
	 * @return Lista de usuário
	 */
	List<User> findAll();

	/**
	 * Lista apenas usuário ativos
	 *
	 * @return Lista de usuário ativos
	 */
	List<User> findAllActive();

	/**
	 * Remove um usuário
	 *
	 * @param id
	 *            ID do usuário a ser removido
	 */
	void delete(Long id);
}
