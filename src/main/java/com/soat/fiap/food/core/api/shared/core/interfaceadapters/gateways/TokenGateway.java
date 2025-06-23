package com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways;

import com.soat.fiap.food.core.api.shared.infrastructure.common.source.TokenSource;
import com.soat.fiap.food.core.api.user.core.domain.model.User;

/**
 * Gateway para manipulação de Tokens.
 */
public class TokenGateway {

	private final TokenSource tokenSource;

	public TokenGateway(TokenSource tokenSource) {
		this.tokenSource = tokenSource;
	}

	/**
	 * Gera um token para o usuário informado.
	 *
	 * @param user
	 *            Usuário para o qual o token será gerado.
	 * @return Token como {@code String}.
	 */
	public String generateToken(User user) {
		return tokenSource.generateToken(user);
	}
}
