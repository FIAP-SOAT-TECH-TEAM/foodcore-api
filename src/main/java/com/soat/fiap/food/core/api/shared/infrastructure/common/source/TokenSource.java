package com.soat.fiap.food.core.api.shared.infrastructure.common.source;

import com.soat.fiap.food.core.api.user.core.domain.model.User;

/**
 * DataSource responsável por operações de Token.
 */
public interface TokenSource {

    /**
     * Gera um token para o usuário informado.
     *
     * @param user Usuário para geração do token.
     * @return Token como String.
     */
    String generateToken(User user);

}