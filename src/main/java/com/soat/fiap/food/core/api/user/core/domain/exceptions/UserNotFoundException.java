package com.soat.fiap.food.core.api.user.core.domain.exceptions;

import com.soat.fiap.food.core.api.shared.core.domain.exceptions.ResourceNotFoundException;

/**
 * Exceção lançada quando um usuário não é encontrado
 */
public class UserNotFoundException extends ResourceNotFoundException {

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(String message, Long id) {
		super(message, id);
	}

	public UserNotFoundException(String resourceName, String fieldName, Object fieldValue) {
		super(resourceName, fieldName, fieldValue);
	}
}
