package com.soat.fiap.food.core.api.user.infrastructure.out.persistence.postgres.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soat.fiap.food.core.api.user.infrastructure.out.persistence.postgres.entity.UserEntity;

/**
 * Repositório Spring Data JPA para UserEntity
 */
@Repository
public interface SpringDataUserRepository extends JpaRepository<UserEntity, Long> {

	/**
	 * Busca usuário por DOCUMENT
	 *
	 * @param document
	 *            DOCUMENT do usuário
	 * @return Optional contendo o usuário ou vazio se não encontrado
	 */
	Optional<UserEntity> findByDocument(String document);

	/**
	 * Busca usuário por EMAIL
	 *
	 * @param email
	 *            EMAIL do usuário
	 * @return Optional contendo o usuário ou vazio se não encontrado
	 */
	Optional<UserEntity> findByEmail(String email);

	/**
	 * Busca usuário por DOCUMENT
	 *
	 * @param username
	 *            USERNAME do usuário
	 * @return Optional contendo o usuário ou vazio se não encontrado
	 */
	Optional<UserEntity> findByUsername(String username);

	/**
	 * Busca um usuário por sua role
	 *
	 * @param roleId
	 *            Role do usuário
	 * @return Optional contendo o usuário ou vazio se não encontrado
	 */

	Optional<UserEntity> findByRoleId(Long roleId);

	/**
	 * Busca o primeiro usuário com o campo guest igual a true.
	 *
	 * @return um {@link Optional} contendo o usuário encontrado ou vazio se não
	 *         houver nenhum.
	 */
	Optional<UserEntity> findFirstByGuestTrue();

	/**
	 * Busca todos os usuários ativos
	 *
	 * @return Lista de usuários ativos
	 */
	List<UserEntity> findByActiveTrue();
}
