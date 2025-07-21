package com.soat.fiap.food.core.api.user.core.domain.exceptions;

import com.soat.fiap.food.core.api.shared.core.domain.exceptions.BusinessException;

/**
 * Exceção lançada quando a senha de um usuário está errada
 */
public class UserIncorrectPasswordException extends BusinessException {

	public UserIncorrectPasswordException(String message) {
		super(message);
	}

	public UserIncorrectPasswordException(String message, Throwable cause) {
		super(message, cause);
	}
}
