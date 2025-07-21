package com.soat.fiap.food.core.api.shared.infrastructure.common.source;

/**
 * Fornecer informaçõe do usuário autenticado no sistema.
 */
public interface AuthenticatedUserSource {

	/**
	 * Obtém o ID do usuário autenticado.
	 *
	 * @return ID do usuário autenticado.
	 */
	Long getUserId();

	/**
	 * Obtém o papel (role) do usuário autenticado.
	 *
	 * @return Papel do usuário (ex: ADMIN, CLIENTE).
	 */
	String getUserRole();
}
