package com.soat.fiap.food.core.api.shared.infrastructure.common.source;

/**
 * DataSource responsável por operações gerais de segurança.
 */
public interface SecuritySource {

	/**
	 * Codifica uma senha em texto puro para sua versão segura.
	 *
	 * @param rawPassword
	 *            Senha em texto puro
	 * @return Senha codificada (hash)
	 */
	String encodePassword(String rawPassword);

	/**
	 * Verifica se a senha em texto puro corresponde à senha codificada.
	 *
	 * @param rawPassword
	 *            Senha em texto puro
	 * @param encodedPassword
	 *            Senha codificada (hash)
	 * @return true se as senhas corresponderem, false caso contrário
	 */
	boolean matches(String rawPassword, String encodedPassword);
}
